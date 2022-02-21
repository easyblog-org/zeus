package top.easyblog.titan.service.oauth.impl.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreatePhoneAuthRequest;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QueryPhoneAuthRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.PhoneAuthService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserService;
import top.easyblog.titan.util.RegexUtils;

/**
 * @author: frank.huang
 * @date: 2022-02-19 17:46
 */
@Component
public class PhoneLoginStrategy extends AbstractLoginStrategy {

    @Autowired
    private PhoneAuthService phoneAuthService;

    public PhoneLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService) {
        super(accountService, userService, randomNicknameService);
    }


    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        PhoneAuth phoneAuth = checkAndGetPhoneInfo(request);
        request.setIdentifier(String.valueOf(phoneAuth.getId()));
        UserDetailsBean userDetailsBean = super.preLoginVerify(request);
        userDetailsBean = processLogin(userDetailsBean, request);
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }

    public PhoneAuth checkAndGetPhoneInfo(LoginRequest request) {
        String[] phoneIdentifier = request.getIdentifier().split("-");
        PhoneAuth phoneAuth = phoneAuthService.queryPhoneAuthDetails(QueryPhoneAuthRequest.builder()
                .phoneAreaCode(phoneIdentifier[0])
                .phone(phoneIdentifier[1]).build());
        if (Objects.isNull(phoneAuth)) {
            throw new BusinessException(ResultCode.IDENTIFIER_NOT_PHONE);
        }
        return phoneAuth;
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        String[] phoneIdentifier = request.getIdentifier().split("-");
        if (phoneIdentifier.length != 2 || Boolean.FALSE.equals(RegexUtils.isPhone(phoneIdentifier[1]))) {
            throw new BusinessException(ResultCode.IDENTIFIER_NOT_PHONE);
        }
        Long phoneAuthId = phoneAuthService.createPhoneAuth(CreatePhoneAuthRequest.builder()
                .phoneAreaCode(phoneIdentifier[0])
                .phone(phoneIdentifier[1])
                .build());
        request.setIdentifier(String.valueOf(phoneAuthId));
        //创建 User & Account
        UserDetailsBean userDetailsBean = processRegister(request);
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }
}
