package top.easyblog.titan.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.*;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.enums.CaptchaSendChannel;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.enums.LoginStatus;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.PhoneAuthService;
import top.easyblog.titan.service.RedisService;
import top.easyblog.titan.service.UserSignInLogService;
import top.easyblog.titan.service.ILoginService;
import top.easyblog.titan.strategy.CaptchaSendStrategyContext;
import top.easyblog.titan.strategy.ICaptchaSendStrategy;
import top.easyblog.titan.strategy.ILoginStrategy;
import top.easyblog.titan.strategy.LoginStrategyContext;
import top.easyblog.titan.util.IdGenerator;
import top.easyblog.titan.util.JsonUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static top.easyblog.titan.constant.LoginConstants.*;

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
    public void sendCaptchaCode(Integer identifierType, String identifier) {
        if(Objects.equals(identifierType,IdentifierType.PHONE.getSubCode()) ||
                Objects.equals(identifierType,IdentifierType.PHONE_CAPTCHA.getSubCode())){
            ICaptchaSendStrategy captchaSendStrategy = CaptchaSendStrategyContext.getCaptchaSendStrategy(CaptchaSendChannel.PHONE_SMS.getCode());
            captchaSendStrategy.sendCaptchaCode(identifier);
        } else if (Objects.equals(identifierType,IdentifierType.E_MAIL.getSubCode())) {
            ICaptchaSendStrategy captchaSendStrategy = CaptchaSendStrategyContext.getCaptchaSendStrategy(CaptchaSendChannel.EMAIL.getCode());
            captchaSendStrategy.sendCaptchaCode(identifier);
        } else{
            throw new UnsupportedOperationException();
        }
    }

    @Override
    @Transaction
    public AuthenticationDetailsBean login(LoginRequest request) {
        ILoginStrategy loginPolicy = LoginStrategyContext.getIdentifyStrategy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ZeusResultCode.INTERNAL_ERROR);
        }
        AuthenticationDetailsBean userDetailsBean = loginPolicy.doLogin(request);
        if (Objects.isNull(userDetailsBean.getUser())) {
            throw new BusinessException(ZeusResultCode.LOGIN_FAILED);
        }
        LoginDetailsBean loginDetailsBean = new LoginDetailsBean();
        loginDetailsBean.setUser(userDetailsBean.getUser());

        //通过登录日志判断用户是否已经登录过
        AccountBean currAccount = userDetailsBean.getUser().getCurrAccount();
        SignInLogBean signInLogBean = signInLogService.querySignInLogDetails(QuerySignInLogRequest.builder()
                .userId(currAccount.getUserId())
                .accountId(currAccount.getId())
                .status(LoginStatus.ONLINE.getCode())
                .build());

        //如果用户已经登录直接返回，否则生成新的token
        return Optional.ofNullable(signInLogBean).map(signInLog -> loginDetailsBean).orElseGet(() -> {
            loginDetailsBean.setToken(generateLoginToken());
            storageToken(request, loginDetailsBean);
            //保存用户登录日志
            CompletableFuture.runAsync(() -> {
                saveLoginLog(request, loginDetailsBean);
            });
            return loginDetailsBean;
        });
    }

    /**
     * 保存登录日志
     *
     * @param request
     * @param loginDetailsBean
     */
    private void saveLoginLog(LoginRequest request, LoginDetailsBean loginDetailsBean) {
        UserDetailsBean userDetailsBean = loginDetailsBean.getUser();
        AccountBean currAccount = userDetailsBean.getCurrAccount();
        CreateSignInLogRequest createSignInLogRequest = Optional.of(userDetailsBean).map(userBean -> {
            log.info("User login log: user_info:{},account_info:{}", JsonUtils.toJSONString(userDetailsBean), JsonUtils.toJSONString(currAccount));
            return CreateSignInLogRequest.builder()
                    .userId(userDetailsBean.getId())
                    .accountId(currAccount.getId())
                    .token(loginDetailsBean.getToken())
                    .status(LoginStatus.ONLINE.getCode())
                    .build();
        }).orElseGet(() -> CreateSignInLogRequest.builder()
                .token(loginDetailsBean.getToken())
                .status(LoginStatus.ONLINE.getCode())
                .build());
        Optional.ofNullable(request.getExtra()).ifPresent(extra -> {
            createSignInLogRequest.setDevice(extra.getDevice());
            createSignInLogRequest.setOperationSystem(extra.getOperationSystem());
            createSignInLogRequest.setIp(extra.getIp());
            createSignInLogRequest.setLocation(extra.getLocation());
        });
        SignInLogBean signInLog = signInLogService.createSignInLog(createSignInLogRequest);
        log.info("Create sign log:{}", JsonUtils.toJSONString(signInLog));
    }

    /**
     * 存储登录token 以及 登录时的用户信息
     *
     * @param request
     * @param loginDetailsBean
     */
    private void storageToken(LoginRequest request, LoginDetailsBean loginDetailsBean) {
        String accountKey = String.format(LoginConstants.LOGIN_TOKEN_KEY_PREFIX, IdentifierType.subCodeOf(request.getIdentifierType()).getCode(), request.getIdentifier());
        String userInfoKey = String.format(LoginConstants.USER_INFO_PREFIX, loginDetailsBean.getToken());
        String token = redisService.storageToken(Lists.newArrayList(accountKey, userInfoKey), loginDetailsBean.getToken(),
                JsonUtils.toJSONString(loginDetailsBean), String.valueOf(LoginConstants.LOGIN_TOKEN_MAX_EXPIRE));
        if (StringUtils.isBlank(token)) {
            log.info("Login failed: internal error,root cause: redis return value is {}", token);
            throw new BusinessException(ZeusResultCode.INTERNAL_ERROR);
        }
        loginDetailsBean.setToken(token);
    }

    public AuthenticationDetailsBean checkLoginHealth(LogoutRequest request) {
        int identifierType = IdentifierType.subCodeOf(request.getIdentifierType()).getCode();

        String accountKey = String.format(LoginConstants.LOGIN_TOKEN_KEY_PREFIX, identifierType, request.getIdentifier());
        String token = redisService.get(accountKey);
        String userInfoJsonStr = null;
        AuthenticationDetailsBean authenticationDetailsBean = AuthenticationDetailsBean.builder().build();

        if (StringUtils.isNotBlank(token)) {
            String userInfoKey = String.format(LoginConstants.USER_INFO_PREFIX, token);
            userInfoJsonStr = redisService.get(userInfoKey);
            authenticationDetailsBean.setUser(StringUtils.isNotBlank(userInfoJsonStr) ? JsonUtils.parseObject(userInfoJsonStr, LoginDetailsBean.class).getUser() : null);
        }

        return authenticationDetailsBean;
    }

    @Override
    public void logout(LogoutRequest request) {
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
                throw new BusinessException(ZeusResultCode.LOGOUT_FAILED);
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
        ILoginStrategy loginPolicy = LoginStrategyContext.getIdentifyStrategy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ZeusResultCode.INTERNAL_ERROR);
        }
        return loginPolicy.doRegister(request);
    }

}
