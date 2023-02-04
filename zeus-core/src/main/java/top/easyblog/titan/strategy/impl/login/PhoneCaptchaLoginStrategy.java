package top.easyblog.titan.strategy.impl.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.*;

import static top.easyblog.titan.constant.LoginConstants.PHONE_LOGIN_CAPTCHA_CODE;

/**
 * 手机验证码登录
 *
 * @author: frank.huang
 * @date: 2022-02-19 19:14
 */
@Component
public class PhoneCaptchaLoginStrategy extends PhoneLoginStrategy {

    @Autowired
    private RedisService redisService;


    public PhoneCaptchaLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService, UserHeaderImgService headerImgService) {
        super(accountService, userService, randomNicknameService, headerImgService);
    }

    @Override
    public Integer getIdentifierType() {
        return IdentifierType.PHONE_CAPTCHA.getSubCode();
    }

    @Transaction
    @Override
    public AuthenticationDetailsBean doLogin(LoginRequest request) {
        String captchaCode = redisService.get(String.format(PHONE_LOGIN_CAPTCHA_CODE, request.getIdentifier()));
        if (StringUtils.isEmpty(captchaCode) || !StringUtils.equals(captchaCode, request.getCredential())) {
            throw new BusinessException(ZeusResultCode.INCORRECT_OR_EXPIRE_CAPTCHA);
        }
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
