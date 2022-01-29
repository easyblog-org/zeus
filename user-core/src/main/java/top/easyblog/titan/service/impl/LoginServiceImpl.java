package top.easyblog.titan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.ILoginService;
import top.easyblog.titan.service.data.AccessAccountService;
import top.easyblog.titan.service.data.AccessPhoneAuthService;
import top.easyblog.titan.service.impl.policy.LoginPolicy;
import top.easyblog.titan.service.impl.policy.LoginPolicyFactory;
import top.easyblog.titan.util.IdGenerator;

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

    @Override
    public LoginDetailsBean login(LoginRequest request) {
        LoginPolicy loginPolicy = LoginPolicyFactory.getLoginPolicy(request.getIdentifierType());
        if (Objects.isNull(loginPolicy)) {
            throw new BusinessException(ResultCode.LOGIN_FAILED);
        }
        LoginDetailsBean loginDetailsBean = loginPolicy.doLogin(request);
        String token = IdGenerator.getLoginToken();
        return null;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public void register(CreateUserRequest request) {

    }

}
