package top.easyblog.titan.service.oauth.impl.policy;

import org.springframework.stereotype.Component;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserHeaderImgService;
import top.easyblog.titan.service.UserService;

/**
 * 手机验证码登录
 *
 * @author: frank.huang
 * @date: 2022-02-19 19:14
 */
@Component
public class PhoneCaptchaLoginStrategy extends PhoneLoginStrategy {


    public PhoneCaptchaLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService, UserHeaderImgService headerImgService) {
        super(accountService, userService, randomNicknameService, headerImgService);
    }

    
    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        PhoneAuth phoneAuth = super.checkAndGetPhoneInfo(request);
        request.setIdentifier(String.valueOf(phoneAuth.getId()));
        AccountBean accountBean = super.preLoginVerify(request);
        UserDetailsBean userDetailsBean = userService.queryUserDetails(QueryUserRequest.builder()
                .id(accountBean.getUserId())
                .sections(LoginConstants.QUERY_HEADER_IMG)
                .build());
        userDetailsBean.setCurrAccount(accountBean);
        return AuthenticationDetailsBean.builder().user(userDetailsBean).build();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doRegister(RegisterUserRequest request) {
        return super.doRegister(request);
    }

}
