package top.easyblog.titan.strategy.impl.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.util.RegexUtils;

import java.util.Objects;

/**
 * 邮箱账号登录
 *
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
@Component
public class EmailLoginStrategy extends AbstractLoginStrategy {

    @Override
    public Integer getIdentifierType() {
        return IdentifierType.E_MAIL.getSubCode();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        AccountBean accountBean = super.preLoginVerify(request);
        UserDetailsBean userDetailsBean = UserDetailsBean.builder().currAccount(accountBean).build();
        userDetailsBean = processLogin(userDetailsBean, request);
        userDetailsBean.setCurrAccount(accountBean);
        return LoginDetailsBean.builder().user(userDetailsBean).build();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        if (Boolean.FALSE.equals(RegexUtils.isEmail(request.getIdentifier()))) {
            throw new BusinessException(ZeusResultCode.IDENTIFIER_NOT_EMAIL);
        }
        QueryAccountRequest queryAccountRequest = QueryAccountRequest.builder()
                .identityType(request.getIdentifierType())
                .identifier(request.getIdentifier()).build();
        AccountBean account = accountService.queryAccountDetails(queryAccountRequest);
        if (Objects.nonNull(account)) {
            throw new BusinessException(ZeusResultCode.EMAIL_ACCOUNT_EXISTS);
        }
        //检查密码是否符合
        if (Boolean.FALSE.equals(StringUtils.equals(request.getCredential(), request.getCredentialAgain()))) {
            throw new BusinessException(ZeusResultCode.PASSWORD_NOT_EQUAL);
        }
        //创建 User & Account
        UserDetailsBean userDetailsBean = processRegister(request);
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }


}
