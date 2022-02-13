package top.easyblog.titan.service.policy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.dao.auto.model.Account;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.enums.AccountStatus;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.impl.UserAccountService;
import top.easyblog.titan.service.impl.UserService;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-01-29 20:54
 */
@Slf4j
@Component
public class UserNameLoginStrategy extends AbstractLoginStrategy {


    public UserNameLoginStrategy(UserAccountService accountService, UserService userService) {
        super(accountService, userService);
    }

    @Override
    public UserDetailsBean doLogin(LoginRequest request) {
        UserDetailsBean userDetailsBean = super.doLogin(request);
        AccountBean currAccount = userDetailsBean.getCurrAccount();
        //check request password and database password
        String databasePassword = currAccount.getCredential();
        String requestPassword = encryptPassword(request.getCredential());
        if (StringUtils.isEmpty(requestPassword) || Boolean.FALSE.equals(requestPassword.equalsIgnoreCase(databasePassword))) {
            throw new BusinessException(ResultCode.PASSWORD_VALID_FAILED);
        }
        QueryUserRequest queryUserRequest = QueryUserRequest.builder()
                .id(currAccount.getUserId())
                .sections(LoginConstants.QUERY_HEADER_IMG)
                .build();
        return userService.queryUserDetails(queryUserRequest);
    }

    @Transaction
    @Override
    public UserDetailsBean doRegister(RegisterUserRequest request) {
        //1. query user information by nick_name
        QueryUserRequest queryUserRequest = QueryUserRequest.builder().nickName(request.getIdentifier()).build();
        UserDetailsBean user = userService.queryUserDetails(queryUserRequest);
        if (Objects.nonNull(user)) {
            //1.1 already has the same nick_name
            log.info("Error: repeat user_name:{} of user:{}", request.getIdentifier(), user.getId());
            throw new BusinessException(ResultCode.REPEAT_USER_NAME);
        }
        //2. pre password validity check
        if (Boolean.FALSE.equals(StringUtils.equals(request.getCredential(), request.getCredentialAgain()))) {
            throw new BusinessException(ResultCode.PASSWORD_NOT_EQUAL);
        }
        if (checkPasswordValid(request.getCredential())) {
            throw new BusinessException(ResultCode.PASSWORD_NOT_VALID);
        }
        //3. create new user
        CreateUserRequest createUserRequest = CreateUserRequest.builder().nickName(request.getIdentifier()).build();
        User newUser = userService.createUser(createUserRequest);

        //4. create new account and bind user_id
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .userId(newUser.getId())
                .identityType((int) IdentifierType.USER_NAME.getCode())
                .identifier(request.getIdentifier())
                .credential(encryptPassword(request.getCredential()))
                .verified(Status.ENABLE.getCode())
                .status(AccountStatus.ACTIVE.getCode())
                .build();
        Account account = accountService.createAccount(createAccountRequest);
        log.info("Create new user account id={} successfully", account.getId());
        return userService.queryUserDetails(QueryUserRequest.builder().id(newUser.getId()).build());
    }


}
