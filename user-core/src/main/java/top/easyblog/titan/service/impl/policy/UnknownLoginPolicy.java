package top.easyblog.titan.service.impl.policy;

import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.RegisterDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ResultCode;

/**
 * @author: frank.huang
 * @date: 2022-01-29 21:04
 */
public class UnknownLoginPolicy implements LoginPolicy {
    @Override
    public LoginDetailsBean doLogin(LoginRequest request) {
        throw new UnsupportedOperationException(ResultCode.INTERNAL_ERROR.getCode());
    }

    @Override
    public RegisterDetailsBean doRegister(RegisterUserRequest request) {
        throw new UnsupportedOperationException(ResultCode.INTERNAL_ERROR.getCode());
    }
}
