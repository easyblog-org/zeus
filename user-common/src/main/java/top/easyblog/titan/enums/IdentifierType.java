package top.easyblog.titan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 用户登录认证类型
 *
 * @author frank.huang
 * @date 2022/01/29 15:09
 */
@Getter
@AllArgsConstructor
public enum IdentifierType {

    UNKNOWN(0, 0, "未知", null),

    E_MAIL(1, 10, "邮箱账号密码登录", "top.easyblog.titan.service.impl.policy.EmailLoginStrategy"),

    PHONE(2, 20, "手机号密码登录", "top.easyblog.titan.service.impl.policy.PhoneLoginStrategy"),

    PHONE_CAPTCHA(2, 21, "手机号验证码登录", "top.easyblog.titan.service.impl.policy.PhoneCaptchaLoginStrategy"),

    QQ(3, 30, "QQ登录", "top.easyblog.titan.service.impl.policy.QQLoginStrategy"),

    WECHAT(4, 40, "微信登录", "top.easyblog.titan.service.impl.policy.WechatLoginStrategy"),

    WEIBO(5, 50, "微博登录", "top.easyblog.titan.service.impl.policy.WeiBoLoginStrategy"),

    GITHUB(6, 60, "GitHub登录", "top.easyblog.titan.service.impl.policy.GitHubLoginStrategy"),

    GITEE(7, 70, "Gitee登录", "top.easyblog.titan.service.impl.policy.GiteeLoginStrategy");


    private final Integer code;
    private final Integer subCode;
    private final String desc;
    private final String policyClassName;


    public static IdentifierType codeOf(Integer code) {
        return Arrays.stream(IdentifierType.values()).filter(type -> Objects.equals(type.code, code)).findAny().orElse(null);
    }

    public static IdentifierType subCodeOf(Integer subCode) {
        return Arrays.stream(IdentifierType.values()).filter(type -> Objects.equals(type.subCode, subCode)).findAny().orElse(null);
    }
}
