package top.easyblog.titan.service.policy;

import org.springframework.stereotype.Component;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;

/**
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
@Component
public class GitHubLoginStrategy implements LoginStrategy {
    @Override
    public UserDetailsBean doLogin(LoginRequest request) {
        return null;
    }

    @Override
    public UserDetailsBean doRegister(RegisterUserRequest request) {
        return null;
    }
}
