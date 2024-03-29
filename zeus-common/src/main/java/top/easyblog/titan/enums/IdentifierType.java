package top.easyblog.titan.enums;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * 用户登录认证类型
 *
 * @author frank.huang
 * @date 2022/01/29 15:09
 */
@Getter
@AllArgsConstructor
public enum IdentifierType {

    UNKNOWN(0, 0, "未知"),

    E_MAIL(1, 10, "邮箱账号密码登录"),

    PHONE(2, 20, "手机号密码登录"),

    PHONE_CAPTCHA(2, 21, "手机号验证码登录"),

    QQ(3, 30, "QQ登录"),

    WECHAT(4, 40, "微信登录"),

    WEIBO(5, 50, "微博登录"),

    GITHUB(6, 60, "GitHub登录"),

    GITEE(7, 70, "Gitee登录");


    private final Integer code;
    private final Integer subCode;
    private final String desc;


    /**
     * 系统用户名密码登录
     */
    public final static Set<IdentifierType> SYSTEM_IDENTITY_TYPE = Sets.immutableEnumSet(E_MAIL, PHONE, PHONE_CAPTCHA);
    /**
     * 第三方登录
     */
    public final static Set<IdentifierType> THIRD_IDENTITY_TYPE = Sets.immutableEnumSet(QQ, WECHAT, WEIBO, GITHUB, GITEE);


    public static IdentifierType codeOf(Integer code) {
        return Arrays.stream(IdentifierType.values()).filter(type -> Objects.equals(type.code, code)).findAny().orElse(null);
    }

    public static IdentifierType subCodeOf(Integer subCode) {
        return Arrays.stream(IdentifierType.values()).filter(type -> Objects.equals(type.subCode, subCode)).findAny().orElse(null);
    }
}
