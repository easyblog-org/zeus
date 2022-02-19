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

    E_MAIL((byte) 1, "邮箱账号密码登录", "top.easyblog.titan.service.impl.policy.EmailLoginStrategy"),

    PHONE((byte) 2, "手机号密码登录", "top.easyblog.titan.service.impl.policy.PhoneLoginStrategy"),

    QQ((byte) 3, "QQ登录", "top.easyblog.titan.service.impl.policy.QQLoginStrategy"),

    WECHAT((byte) 4, "微信登录", "top.easyblog.titan.service.impl.policy.WechatLoginStrategy"),

    WEIBO((byte) 5, "微博登录", "top.easyblog.titan.service.impl.policy.WeiBoLoginStrategy"),

    GITHUB((byte) 6, "GitHub登录", "top.easyblog.titan.service.impl.policy.GitHubLoginStrategy"),

    GITEE((byte) 7, "Gitee登录", "top.easyblog.titan.service.impl.policy.GiteeLoginStrategy");


    private final byte code;
    private final String desc;
    private final String policyClassName;


    public static IdentifierType codeOf(Byte code) {
        return Arrays.stream(IdentifierType.values()).filter(type -> type.code == code).findAny().orElse(null);
    }
}
