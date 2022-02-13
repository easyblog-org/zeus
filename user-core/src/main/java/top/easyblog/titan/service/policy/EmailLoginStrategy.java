package top.easyblog.titan.service.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.data.AccessAccountService;

/**
 * 用户使用邮箱账号登录
 *
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
@Component
public class EmailLoginStrategy implements LoginStrategy {

    @Autowired
    private AccessAccountService accessAccountService;

    @Override
    public UserDetailsBean doLogin(LoginRequest request) {

        return null;
    }

    @Override
    public UserDetailsBean doRegister(RegisterUserRequest request) {
        return null;
    }


    private void parseLoginPassword(LoginRequest request) {

    }
}
