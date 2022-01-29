package top.easyblog.titan.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 第三方App类型
 *
 * @author frank.huang
 * @date 2022/01/29 14:16
 */
@Getter
@AllArgsConstructor
public enum ThirdPartAppType {

    UNKNOWN((byte) 0, "unknown"),
    /**
     * QQ
     */
    QQ((byte) 10, "qq"),

    /**
     * 微信
     */
    WeChat((byte) 20, "WeChat"),

    /**
     * GitHub
     */
    GitHub((byte) 30, "GitHub"),

    /**
     * GitLab
     */
    GitLAb((byte) 40, "GitLab"),

    /**
     * 微博
     */
    MicroBlog((byte) 50, "MicroBlog");


    private final byte code;
    private final String appType;


    public static ThirdPartAppType codeOf(Byte code) {
        return Arrays.stream(ThirdPartAppType.values()).filter(type -> type.code == code).findAny().orElse(UNKNOWN);
    }

    public static ThirdPartAppType nameOf(String name) {
        return Arrays.stream(ThirdPartAppType.values()).filter(type -> type.appType.equalsIgnoreCase(name)).findAny().orElse(UNKNOWN);
    }

}
