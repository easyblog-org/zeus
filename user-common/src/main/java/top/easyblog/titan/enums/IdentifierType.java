package top.easyblog.titan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户登录认证类型
 *
 * @author frank.huang
 * @date 2022/01/29 15:09
 */
@Getter
@AllArgsConstructor
public enum IdentifierType {

    UNKNOWN((byte) 0, (byte) 0, "未知", null),

    E_MAIL((byte) 1, (byte) 10, "邮箱账号密码登录", "top.easyblog.titan.service.impl.policy.EmailLoginStrategy"),

    PHONE((byte) 2, (byte) 20, "手机号密码登录", "top.easyblog.titan.service.impl.policy.PhoneLoginStrategy"),

    PHONE_CAPTCHA((byte) 2, (byte) 21, "手机号验证码登录", "top.easyblog.titan.service.impl.policy.PhoneCaptchaLoginStrategy"),

    QQ((byte) 3, (byte) 30, "QQ登录", "top.easyblog.titan.service.impl.policy.QQLoginStrategy"),

    WECHAT((byte) 4, (byte) 40, "微信登录", "top.easyblog.titan.service.impl.policy.WechatLoginStrategy"),

    WEIBO((byte) 5, (byte) 50, "微博登录", "top.easyblog.titan.service.impl.policy.WeiBoLoginStrategy"),

    GITHUB((byte) 6, (byte) 60, "GitHub登录", "top.easyblog.titan.service.impl.policy.GitHubLoginStrategy"),

    GITEE((byte) 7, (byte) 70, "Gitee登录", "top.easyblog.titan.service.impl.policy.GiteeLoginStrategy");


    private final byte code;
    private final byte subCode;
    private final String desc;
    private final String policyClassName;


    public static IdentifierType codeOf(Byte code) {
        return Arrays.stream(IdentifierType.values()).filter(type -> type.code == code).findAny().orElse(null);
    }

    public static IdentifierType subCodeOf(Byte subCode) {
        return Arrays.stream(IdentifierType.values()).filter(type -> type.subCode == subCode).findAny().orElse(null);
    }
}
