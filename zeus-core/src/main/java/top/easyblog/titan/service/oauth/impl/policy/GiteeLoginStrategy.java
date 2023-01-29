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
import top.easyblog.titan.service.UserHeaderImgService;
import top.easyblog.titan.service.UserService;
import top.easyblog.titan.service.oauth.ILoginService;

import java.util.Objects;

/**
 * Gitee第三方登录
 *
 * @author frank.huang
 * @date 2022/02/21 16:12
 */
@Slf4j
@Component
public class GiteeLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private ILoginService loginService;

    public GiteeLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService, UserHeaderImgService headerImgService) {
        super(accountService, userService, randomNicknameService, headerImgService);
    }


    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        AccountBean accountBean = super.preLoginVerify(request);
        UserDetailsBean userDetailsBean = userService.queryUserDetails(QueryUserRequest.builder()
                .id(accountBean.getUserId()).sections(LoginConstants.QUERY_HEADER_IMG).build());
        userDetailsBean.setCurrAccount(accountBean);
        log.info("Gitee user: {} login successfully!", request.getIdentifier());
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
        log.info("Gitee user: {} start register as user!", request.getIdentifier());
        AccountBean account = accountService.queryAccountDetails(QueryAccountRequest.builder()
                .identityType(IdentifierType.GITEE.getCode()).identifier(request.getIdentifier()).build());
        if (Objects.nonNull(account)) {
            log.info("Gitee user: {} already register as user,redirect to login...", request.getIdentifier());
            return redirectToLogin(request);
        }
        request.setStatus(AccountStatus.ACTIVE.getCode());
        request.setVerified(Status.ENABLE.getCode());
        processRegister(request);
        log.info("GitHub user: {} register successfully!", request.getIdentifier());
        return redirectToLogin(request);
    }


    private AuthenticationDetailsBean redirectToLogin(RegisterUserRequest request) {
        log.info("Gitee user: {} goto login...", request.getIdentifier());
        return loginService.login(LoginRequest.builder()
                .identifierType(IdentifierType.GITEE.getSubCode())
                .identifier(request.getIdentifier())
                .build());
    }
}
