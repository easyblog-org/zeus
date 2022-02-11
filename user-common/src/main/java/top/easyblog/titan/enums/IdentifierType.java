package top.easyblog.titan.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户登录认证类型
 *
 * @author frank.huang
 * @date 2022/01/29 15:09
 */
@Getter
@AllArgsConstructor
public enum IdentifierType {

    USER_NAME((byte) 1, "账号密码登录", "top.easyblog.titan.service.impl.policy.UserNameLoginPolicy"),

    E_MAIL((byte) 2, "邮箱账号登录", "top.easyblog.titan.service.impl.policy.EmailLoginPolicy"),

    PHONE((byte) 3, "手机号登录", "top.easyblog.titan.service.impl.policy.PhoneLoginPolicy"),

    QQ((byte) 4, "QQ登录", "top.easyblog.titan.service.impl.policy.QQLoginPolicy"),

    WECHAT((byte) 5, "微信登录", "top.easyblog.titan.service.impl.policy.WechatLoginPolicy"),

    WEIBO((byte) 6, "微博登录", "top.easyblog.titan.service.impl.policy.WeiBoLoginPolicy"),

    GITHUB((byte) 7, "GitHub登录", "top.easyblog.titan.service.impl.policy.GitHubLoginPolicy"),

    GITEE((byte) 8, "Gitee登录", "top.easyblog.titan.service.impl.policy.GiteeLoginPolicy");


    private final byte code;
    private final String desc;
    private final String policyClassName;


    public static IdentifierType codeOf(Byte code) {
        return Arrays.stream(IdentifierType.values()).filter(type -> type.code == code).findAny().orElse(null);
    }
}
