package top.easyblog.titan.service.oauth.impl.policy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserService;

/**
 * Gitee第三方登录
 *
 * @author frank.huang
 * @date 2022/02/21 16:12
 */
@Slf4j
@Component
public class GiteeLoginStrategy extends AbstractLoginStrategy {

    public GiteeLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService) {
        super(accountService, userService, randomNicknameService);
    }

    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        return null;
    }

    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        return null;
    }
}
