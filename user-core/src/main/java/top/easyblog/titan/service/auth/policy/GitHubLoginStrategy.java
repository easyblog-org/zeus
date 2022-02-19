package top.easyblog.titan.service.auth.policy;

import org.springframework.stereotype.Component;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserService;

/**
 * GitHub第三方登录
 *
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
@Component
public class GitHubLoginStrategy extends AbstractLoginStrategy {

    public GitHubLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService) {
        super(accountService, userService, randomNicknameService);
    }

    @Override
    public UserDetailsBean doLogin(LoginRequest request) {
        return null;
    }

    @Override
    public UserDetailsBean doRegister(RegisterUserRequest request) {
        return null;
    }
}
