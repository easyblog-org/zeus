package top.easyblog.titan.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.SignInLogBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.enums.LoginStatus;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.ILoginService;
import top.easyblog.titan.service.policy.LoginStrategy;
import top.easyblog.titan.service.policy.LoginStrategyFactory;
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

    @Override
    @Transaction
    public LoginDetailsBean login(LoginRequest request) {
        LoginStrategy loginPolicy = LoginStrategyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        UserDetailsBean userDetailsBean = loginPolicy.doLogin(request);
        if (Objects.isNull(userDetailsBean)) {
            throw new BusinessException(ResultCode.LOGIN_FAILED);
        }
        LoginDetailsBean loginDetailsBean = new LoginDetailsBean();
        loginDetailsBean.setUser(userDetailsBean);
        loginDetailsBean.setToken(generateLoginToken());

        //保存用户登录日志
        CreateSignInLogRequest createSignInLogRequest = CreateSignInLogRequest.builder()
                .userId(userDetailsBean.getId())
                .status(LoginStatus.ACTIVE.getCode())
                .device(request.getDevice())
                .operationSystem(request.getOperationSystem())
                .ip(request.getIp())
                .location(request.getLocation())
                .build();
        SignInLogBean signInLog = signInLogService.createSignInLog(createSignInLogRequest);
        loginDetailsBean.setSignInLog(signInLog);

        storageToken(request, loginDetailsBean);
        return loginDetailsBean;
    }


    private void storageToken(LoginRequest request, LoginDetailsBean loginDetailsBean) {
        String accountKey = String.format(LoginConstants.LOGIN_TOKEN_KEY_PREFIX, request.getIdentifierType(), request.getIdentifier());
        String userInfoKey = String.format(LoginConstants.USER_INFO_PREFIX, loginDetailsBean.getToken());
        String token = redisService.storageToken(Lists.newArrayList(accountKey, userInfoKey), loginDetailsBean.getToken(),
                JsonUtils.toJSONString(loginDetailsBean), String.valueOf(LoginConstants.LOGIN_TOKEN_MAX_EXPIRE));
        if (StringUtils.isBlank(token)) {
            log.info("Login failed: internal error,root cause: redis return value is {}", token);
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        loginDetailsBean.setToken(token);
    }

    public UserDetailsBean checkLoginHealth(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ResultCode.REQUIRED_PARAM_TOKEN_NOT_EXISTS);
        }
        String userInfoJsonStr = redisService.get(token);
        return StringUtils.isNotBlank(userInfoJsonStr) ? JsonUtils.parseObject(userInfoJsonStr, UserDetailsBean.class) : null;
    }

    @Override
    public void logout(LogoutRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        //将redis登录时保存的token（如果还未过期）删除
        String accountKey = String.format(LoginConstants.LOGIN_TOKEN_KEY_PREFIX, request.getIdentifierType(), request.getIdentifier());
        String userInfoJsonStr = redisService.logout(Lists.newArrayList(accountKey));
        if (StringUtils.isBlank(userInfoJsonStr)) {
            log.info("Logout failed: internal error,root cause: redis return value is {}", userInfoJsonStr);
            return;
        }

        //退出成功
        CompletableFuture.runAsync(() -> {
            //更新用户账户状态为退出
            LoginDetailsBean loginDetailsBean = JsonUtils.parseObject(userInfoJsonStr, LoginDetailsBean.class);
            SignInLogBean signInLog = loginDetailsBean.getSignInLog();
            Optional.ofNullable(signInLog).ifPresent(signInLogBean -> {
                signInLogService.updateSignLog(UpdateSignInLogRequest.builder()
                        .id(signInLog.getId())
                        .status(LoginStatus.UN_ACTIVE.getCode())
                        .build());
            });
        });
    }

    @Override
    public UserDetailsBean register(RegisterUserRequest request) {
        LoginStrategy loginPolicy = LoginStrategyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        return loginPolicy.doRegister(request);
    }

}
