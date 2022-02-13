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

    USER_NAME((byte) 1, "账号密码登录", "top.easyblog.titan.service.impl.policy.UserNameLoginStrategy"),

    E_MAIL((byte) 2, "邮箱账号登录", "top.easyblog.titan.service.impl.policy.EmailLoginStrategy"),

    PHONE((byte) 3, "手机号登录", "top.easyblog.titan.service.impl.policy.PhoneLoginStrategy"),

    QQ((byte) 4, "QQ登录", "top.easyblog.titan.service.impl.policy.QQLoginStrategy"),

    WECHAT((byte) 5, "微信登录", "top.easyblog.titan.service.impl.policy.WechatLoginStrategy"),

    WEIBO((byte) 6, "微博登录", "top.easyblog.titan.service.impl.policy.WeiBoLoginStrategy"),

    GITHUB((byte) 7, "GitHub登录", "top.easyblog.titan.service.impl.policy.GitHubLoginStrategy"),

    GITEE((byte) 8, "Gitee登录", "top.easyblog.titan.service.impl.policy.GiteeLoginStrategy");


    private final byte code;
    private final String desc;
    private final String policyClassName;


    public static IdentifierType codeOf(Byte code) {
        return Arrays.stream(IdentifierType.values()).filter(type -> type.code == code).findAny().orElse(null);
    }
}
