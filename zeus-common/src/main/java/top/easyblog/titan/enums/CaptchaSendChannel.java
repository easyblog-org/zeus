package top.easyblog.titan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: frank.huang
 * @date: 2022-02-13 21:50
 */
@Getter
@AllArgsConstructor
public enum CaptchaSendChannel {
    /**
     * 手机短信渠道
     */
    PHONE_SMS(1),
    /**
     * 邮箱
     */
    EMAIL(2);

    private final int code;
}
