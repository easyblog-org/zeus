package top.easyblog.titan.service.oauth.impl.policy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.enums.AccountStatus;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserService;
import top.easyblog.titan.service.oauth.ILoginService;

import java.util.Objects;

/**
 * GitHub第三方登录
 *
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
@Slf4j
@Component
public class GitHubLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private ILoginService loginService;

    public GitHubLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService) {
        super(accountService, userService, randomNicknameService);
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        UserDetailsBean userDetailsBean = super.preLoginVerify(request);
        userDetailsBean = userService.queryUserDetails(QueryUserRequest.builder()
                .id(userDetailsBean.getCurrAccount().getUserId())
                .sections(LoginConstants.QUERY_HEADER_IMG)
                .build());
        log.info("GitHub user: {} login successfully!", request.getIdentifier());
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }

    /**
     * identifierType: IdentifierType.GitHub
     * identifier：openId
     *
     * @param request
     * @return
     */
    @Transaction
    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        log.info("GitHub user: {} start register as user!", request.getIdentifier());
        AccountBean account = accountService.queryAccountDetails(QueryAccountRequest.builder()
                .identityType(IdentifierType.GITHUB.getCode())
                .identifier(request.getIdentifier())
                .build());
        if (Objects.nonNull(account)) {
            log.info("GitHub user: {} already register as user,redirect to login...", request.getIdentifier());
            return redirectToLogin(request);
        }
        request.setStatus(AccountStatus.ACTIVE.getCode());
        request.setVerified(Status.ENABLE.getCode());
        processRegister(request);
        log.info("GitHub user: {} register successfully!", request.getIdentifier());
        return redirectToLogin(request);
    }


    private AuthenticationDetailsBean redirectToLogin(RegisterUserRequest request) {
        log.info("GitHub user: {} goto login...", request.getIdentifier());
        return loginService.login(LoginRequest.builder()
                .identifierType(IdentifierType.GITHUB.getSubCode())
                .identifier(request.getIdentifier())
                .build());
    }
}
