package top.easyblog.titan.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.SignInLogBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QuerySignInLogRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.ILoginService;
import top.easyblog.titan.service.data.AccessAccountService;
import top.easyblog.titan.service.data.AccessPhoneAuthService;
import top.easyblog.titan.service.policy.LoginPolicy;
import top.easyblog.titan.service.policy.LoginPolicyFactory;
import top.easyblog.titan.util.IdGenerator;
import top.easyblog.titan.util.JsonUtils;

/**
 * @author frank.huang
 * @date 2022/01/29 14:09
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AccessAccountService accessAccountService;

    @Autowired
    private AccessPhoneAuthService phoneAuthService;

    @Autowired
    private UserSignInLogService signInLogService;

    @Autowired
    private RedisService redisService;

    @Override
    @Transaction
    public LoginDetailsBean login(LoginRequest request, HttpServletRequest httpServletRequest) {
        LoginPolicy loginPolicy = LoginPolicyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        LoginDetailsBean loginDetailsBean = loginPolicy.doLogin(request);
        if (Objects.isNull(loginDetailsBean) || Objects.isNull(loginDetailsBean.getUser())) {
            throw new BusinessException(ResultCode.LOGIN_FAILED);
        }
        loginDetailsBean.setToken(IdGenerator.getLoginToken());
        storageToken(loginDetailsBean);
        return loginDetailsBean;
    }


    private void storageToken(LoginDetailsBean loginDetailsBean) {
        redisService.set(loginDetailsBean.getToken(), JsonUtils.toJSONString(loginDetailsBean.getUser()), LoginConstants.LOG_IN_EXPIRE, TimeUnit.SECONDS);
    }

    public UserDetailsBean checkLoginHealth(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ResultCode.REQUIRED_PARAM_TOKEN_NOT_EXISTS);
        }
        String userInfoJsonStr = redisService.get(token);
        return StringUtils.isNotBlank(userInfoJsonStr) ? JsonUtils.parseObject(userInfoJsonStr, UserDetailsBean.class) : null;
    }

    @Override
    public void logout(String token, HttpServletRequest httpServletRequest) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ResultCode.REQUIRED_PARAM_TOKEN_NOT_EXISTS);
        }
        String userInfoJsonStr = redisService.get(token);
        if (StringUtils.isEmpty(userInfoJsonStr)) {
            //检查是否过期或者token无效，如果token找不到直接返回
            return;
        }
        UserDetailsBean userDetailsBean = JsonUtils.parseObject(userInfoJsonStr, UserDetailsBean.class);
        Long id = userDetailsBean.getId();
        
        List<SignInLogBean> signInLogs = userDetailsBean.getSignInLogs();
        SignInLogBean signInLogBean = signInLogService.querySignInLogDetails(QuerySignInLogRequest.builder()
                .userId(id).status(Status.ENABLE.getCode()).build());
        redisService.delete(token);
    }

    @Override
    public UserDetailsBean register(RegisterUserRequest request, HttpServletRequest httpServletRequest) {
        LoginPolicy loginPolicy = LoginPolicyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        return loginPolicy.doRegister(request);
    }

}
