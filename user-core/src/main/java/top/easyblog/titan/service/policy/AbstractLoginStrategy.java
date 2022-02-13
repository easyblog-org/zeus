package top.easyblog.titan.service.policy;

import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.enums.AccountStatus;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.impl.UserAccountService;
import top.easyblog.titan.service.impl.UserService;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-02-13 21:55
 */
public abstract class AbstractLoginStrategy implements LoginStrategy {

    protected UserAccountService accountService;

    protected UserService userService;


    public AbstractLoginStrategy(UserAccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public UserDetailsBean doLogin(LoginRequest request) {
        QueryAccountRequest queryAccountRequest = QueryAccountRequest.builder()
                .identityType(request.getIdentifierType().intValue())
                .identifier(request.getIdentifier())
                .build();
        AccountBean accountBean = accountService.queryAccountDetails(queryAccountRequest);
        if (Objects.isNull(accountBean)) {
            //check and found that the account is not exists
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_FOUND);
        }
        if (AccountStatus.DELETE.getCode().equals(accountBean.getStatus())) {
            //用户账户已经删除
            throw new BusinessException(ResultCode.ACCOUNT_IS_DELETE);
        }
        if (AccountStatus.FREEZE.getCode().equals(accountBean.getStatus())) {
            //账户被封还未解封
            throw new BusinessException(ResultCode.ACCOUNT_IS_FREEZE);
        }
        return UserDetailsBean.builder().currAccount(accountBean).build();
    }

    @Override
    public UserDetailsBean doRegister(RegisterUserRequest request) {
        return null;
    }
}
