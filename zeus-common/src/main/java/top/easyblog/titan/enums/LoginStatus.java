package top.easyblog.titan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: frank.huang
 * @date: 2022-02-13 21:50
 */
@Getter
@AllArgsConstructor
public enum LoginStatus {
    ONLINE(1, "登录/在线"),
    OFFLINE(0, "退出/离线");

    private final int code;
    private final String desc;
}
