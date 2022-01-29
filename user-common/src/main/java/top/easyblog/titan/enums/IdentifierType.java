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

    UNKNOWN((byte) 0, "unknown", "top.easyblog.titan.service.impl.policy.SysUserLoginPolicy"),

    SYSTEM((byte) 1, "system", "top.easyblog.titan.service.impl.policy.SysUserLoginPolicy"),

    E_MAIL((byte) 2, "e_mail", "top.easyblog.titan.service.impl.policy.SysUserLoginPolicy"),

    PHONE((byte) 3, "phone", "top.easyblog.titan.service.impl.policy.SysUserLoginPolicy"),

    QQ((byte) 4, "qq", "top.easyblog.titan.service.impl.policy.SysUserLoginPolicy"),

    WECHAT((byte) 5, "wechat", "top.easyblog.titan.service.impl.policy.SysUserLoginPolicy"),

    WEIBO((byte) 6, "weibo", "top.easyblog.titan.service.impl.policy.SysUserLoginPolicy"),

    GITHUB((byte) 7, "github", "top.easyblog.titan.service.impl.policy.GitHubLoginPolicy"),

    GITEE((byte) 7, "gitee", "top.easyblog.titan.service.impl.policy.SysUserLoginPolicy");


    private final byte code;
    private final String desc;
    private final String policyClassName;


    public static IdentifierType codeOf(Byte code) {
        return Arrays.stream(IdentifierType.values()).filter(type -> type.code == code).findAny().orElse(UNKNOWN);
    }
}
