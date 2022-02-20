package top.easyblog.titan.service.auth.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.SignInLogBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.enums.LoginStatus;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.PhoneAuthService;
import top.easyblog.titan.service.RedisService;
import top.easyblog.titan.service.UserSignInLogService;
import top.easyblog.titan.service.auth.ILoginService;
import top.easyblog.titan.service.auth.ILoginStrategy;
import top.easyblog.titan.service.auth.LoginStrategyFactory;
import top.easyblog.titan.util.JsonUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author frank.huang
 * @date 2022/01/29 14:09
 */
@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserSignInLogService signInLogService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PhoneAuthService phoneAuthService;

    @Override
    @Transaction
    public AuthenticationDetailsBean login(LoginRequest request) {
        ILoginStrategy loginPolicy = LoginStrategyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        AuthenticationDetailsBean userDetailsBean = loginPolicy.doLogin(request);
        if (Objects.isNull(userDetailsBean)) {
            throw new BusinessException(ResultCode.LOGIN_FAILED);
        }
        LoginDetailsBean loginDetailsBean = new LoginDetailsBean();
        loginDetailsBean.setUser(userDetailsBean.getUser());
        loginDetailsBean.setToken(generateLoginToken());

        storageToken(request, loginDetailsBean);

        //保存用户登录日志
        CompletableFuture.runAsync(() -> {
            saveLoginLog(request, loginDetailsBean);
        });

        return loginDetailsBean;
    }

    /**
     * 保存登录日志
     *
     * @param request
     * @param loginDetailsBean
     */
    private void saveLoginLog(LoginRequest request, LoginDetailsBean loginDetailsBean) {
        UserDetailsBean userDetailsBean = loginDetailsBean.getUser();
        CreateSignInLogRequest createSignInLogRequest = CreateSignInLogRequest.builder()
                .userId(userDetailsBean.getId())
                .token(loginDetailsBean.getToken())
                .status(LoginStatus.ONLINE.getCode())
                .build();
        Optional.ofNullable(request.getExtra()).ifPresent(extra -> {
            createSignInLogRequest.setDevice(extra.getDevice());
            createSignInLogRequest.setOperationSystem(extra.getOperationSystem());
            createSignInLogRequest.setIp(extra.getIp());
            createSignInLogRequest.setLocation(extra.getLocation());
        });
        SignInLogBean signInLog = signInLogService.createSignInLog(createSignInLogRequest);
        log.info("Create sign log:{}", JsonUtils.toJSONString(signInLog));
    }


    private void storageToken(LoginRequest request, LoginDetailsBean loginDetailsBean) {
        String accountKey = String.format(LoginConstants.LOGIN_TOKEN_KEY_PREFIX, IdentifierType.subCodeOf(request.getIdentifierType()).getCode(), request.getIdentifier());
        String userInfoKey = String.format(LoginConstants.USER_INFO_PREFIX, loginDetailsBean.getToken());
        String token = redisService.storageToken(Lists.newArrayList(accountKey, userInfoKey), loginDetailsBean.getToken(),
                JsonUtils.toJSONString(loginDetailsBean), String.valueOf(LoginConstants.LOGIN_TOKEN_MAX_EXPIRE));
        if (StringUtils.isBlank(token)) {
            log.info("Login failed: internal error,root cause: redis return value is {}", token);
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        loginDetailsBean.setToken(token);
    }

    public AuthenticationDetailsBean checkLoginHealth(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ResultCode.REQUIRED_PARAM_TOKEN_NOT_EXISTS);
        }
        String userInfoJsonStr = redisService.get(token);
        return StringUtils.isNotBlank(userInfoJsonStr) ?
                AuthenticationDetailsBean.builder().user(JsonUtils.parseObject(userInfoJsonStr, UserDetailsBean.class)).build() : null;
    }

    @Override
    public void logout(LogoutRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        int identifierType = IdentifierType.subCodeOf(request.getIdentifierType()).getCode();
        if (Objects.equals(IdentifierType.PHONE.getCode(), identifierType)) {
            String[] phoneIdentifier = request.getIdentifier().split("-");
            PhoneAuth phoneAuth = phoneAuthService.queryPhoneAuthDetails(QueryPhoneAuthRequest.builder()
                    .phoneAreaCode(phoneIdentifier[0])
                    .phone(phoneIdentifier[1]).build());
            request.setIdentifier(String.valueOf(phoneAuth.getId()));
        }
        //将redis登录时保存的token（如果还未过期）删除
        String accountKey = String.format(LoginConstants.LOGIN_TOKEN_KEY_PREFIX, identifierType, request.getIdentifier());
        String token = redisService.get(accountKey);
        if (StringUtils.isNotBlank(token)) {
            String userInfoJsonStr = redisService.logout(Lists.newArrayList(accountKey));
            if (StringUtils.isBlank(userInfoJsonStr)) {
                log.info("Logout failed: internal error,root cause: redis return value is {}", userInfoJsonStr);
                throw new BusinessException(ResultCode.LOGOUT_FAILED);
            }
        }

        //退出成功
        CompletableFuture.runAsync(() -> {
            //更新用户账户状态为退出
            Optional.ofNullable(signInLogService.querySignInLogDetails(QuerySignInLogRequest.builder()
                            .userId(request.getUserId()).token(token).build()))
                    .ifPresent(signInLogBean -> {
                        signInLogService.updateSignLog(UpdateSignInLogRequest.builder()
                                .id(signInLogBean.getId()).status(LoginStatus.OFFLINE.getCode()).build());
                        log.info("Update the account {} of user [id={}] log out successfully!", request.getIdentifier(), request.getUserId());
                    });
        });
    }

    @Override
    public AuthenticationDetailsBean register(RegisterUserRequest request) {
        ILoginStrategy loginPolicy = LoginStrategyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        return loginPolicy.doRegister(request);
    }

}
