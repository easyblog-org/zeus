package top.easyblog.titan.service.auth.policy;

import org.springframework.stereotype.Component;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserService;

/**
 * @author: frank.huang
 * @date: 2022-02-19 19:14
 */
@Component
public class PhoneCaptchaLoginStrategy extends PhoneLoginStrategy {


    public PhoneCaptchaLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService) {
        super(accountService, userService, randomNicknameService);
    }

    @Transaction
    @Override
    public UserDetailsBean doLogin(LoginRequest request) {
        PhoneAuth phoneAuth = super.checkAndGetPhoneInfo(request);
        request.setIdentifier(String.valueOf(phoneAuth.getId()));
        UserDetailsBean userDetailsBean = super.preLoginVerify(request);
        return userService.queryUserDetails(QueryUserRequest.builder()
                .id(userDetailsBean.getCurrAccount().getUserId())
                .sections(LoginConstants.QUERY_HEADER_IMG)
                .build());
    }

    @Transaction
    @Override
    public UserDetailsBean doRegister(RegisterUserRequest request) {
        return super.doRegister(request);
    }

}
