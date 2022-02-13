package top.easyblog.titan.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import top.easyblog.titan.service.policy.LoginPolicy;
import top.easyblog.titan.service.policy.LoginPolicyFactory;
import top.easyblog.titan.util.JsonUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 14:09
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserSignInLogService signInLogService;

    @Autowired
    private RedisService redisService;

    @Override
    @Transaction
    public LoginDetailsBean login(LoginRequest request) {
        LoginPolicy loginPolicy = LoginPolicyFactory.getLoginPolicy(request.getIdentifierType());
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
        storageToken(request, loginDetailsBean);
        return loginDetailsBean;
    }


    private void storageToken(LoginRequest request, LoginDetailsBean loginDetailsBean) {
        String accountKey = String.format(LoginConstants.LOGIN_TOKEN_KEY_PREFIX, request.getIdentifierType(), request.getIdentifier());
        String userKey = String.format(LoginConstants.USER_INFO_PREFIX, loginDetailsBean.getToken());
        String token = redisService.storageToken(Lists.newArrayList(accountKey, userKey), loginDetailsBean.getToken(),
                JsonUtils.toJSONString(loginDetailsBean.getUser()), String.valueOf(LoginConstants.LOGIN_TOKEN_MAX_EXPIRE));
        if (StringUtils.isBlank(token)) {
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
    public void logout(String token) {
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
        String key = String.format(LoginConstants.LOGIN_TOKEN_KEY_PREFIX, token);
        redisService.delete(key);
    }

    @Override
    public UserDetailsBean register(RegisterUserRequest request) {
        LoginPolicy loginPolicy = LoginPolicyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        return loginPolicy.doRegister(request);
    }

}
