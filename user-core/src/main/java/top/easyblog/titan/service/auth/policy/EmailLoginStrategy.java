package top.easyblog.titan.service.auth.policy;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserService;
import top.easyblog.titan.util.RegexUtils;

import java.util.Objects;

/**
 * 用户使用邮箱账号登录
 *
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
@Component
public class EmailLoginStrategy extends AbstractLoginStrategy {

    public EmailLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService) {
        super(accountService, userService, randomNicknameService);
    }

    @Transaction
    @Override
    public UserDetailsBean doLogin(LoginRequest request) {
        UserDetailsBean userDetailsBean = super.preLoginVerify(request);
        return processLogin(userDetailsBean, request);
    }

    @Transaction
    @Override
    public UserDetailsBean doRegister(RegisterUserRequest request) {
        if (Boolean.FALSE.equals(RegexUtils.isEmail(request.getIdentifier()))) {
            throw new BusinessException(ResultCode.IDENTIFIER_NOT_EMAIL);
        }
        QueryAccountRequest queryAccountRequest = QueryAccountRequest.builder()
                .identityType(request.getIdentifierType().intValue())
                .identifier(request.getIdentifier()).build();
        AccountBean account = accountService.queryAccountDetails(queryAccountRequest);
        if (Objects.nonNull(account)) {
            throw new BusinessException(ResultCode.EMAIL_ACCOUNT_EXISTS);
        }
        //检查密码是否符合
        if (checkPasswordValid(request.getCredential())) {
            throw new BusinessException(ResultCode.PASSWORD_NOT_VALID);
        }
        if (Boolean.FALSE.equals(StringUtils.equals(request.getCredential(), request.getCredentialAgain()))) {
            throw new BusinessException(ResultCode.PASSWORD_NOT_EQUAL);
        }
        //创建 User & Account
        return processRegister(request);
    }

}
