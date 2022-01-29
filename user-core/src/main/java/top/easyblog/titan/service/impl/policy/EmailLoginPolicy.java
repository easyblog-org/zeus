package top.easyblog.titan.service.impl.policy;

import org.springframework.beans.factory.annotation.Autowired;
import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.RegisterDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.data.AccessAccountService;

/**
 * 用户使用邮箱账号登录
 *
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
public class EmailLoginPolicy implements LoginPolicy {

    @Autowired
    private AccessAccountService accessAccountService;

    @Override
    public LoginDetailsBean doLogin(LoginRequest request) {

        return null;
    }

    @Override
    public RegisterDetailsBean doRegister(RegisterUserRequest request) {
        return null;
    }


    private void parseLoginPassword(LoginRequest request) {

    }
}
