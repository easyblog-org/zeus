package top.easyblog.titan.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.ILoginService;
import top.easyblog.titan.service.RedisService;
import top.easyblog.titan.service.data.AccessAccountService;
import top.easyblog.titan.service.data.AccessPhoneAuthService;
import top.easyblog.titan.service.impl.policy.LoginPolicy;
import top.easyblog.titan.service.impl.policy.LoginPolicyFactory;
import top.easyblog.titan.util.IdGenerator;
import top.easyblog.titan.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    public void logout(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ResultCode.REQUIRED_PARAM_TOKEN_NOT_EXISTS);
        }
        redisService.delete(token);
    }

    @Override
    public void register(RegisterUserRequest request) {
        LoginPolicy loginPolicy = LoginPolicyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        loginPolicy.doRegister(request);
    }

}
