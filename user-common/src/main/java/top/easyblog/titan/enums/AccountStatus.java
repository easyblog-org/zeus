package top.easyblog.titan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author frank.huang
 * @date 2022/01/30 09:37
 */
@Getter
@AllArgsConstructor
public enum AccountStatus {

    PRE_ACTIVE(0, "待激活", true),
    ACTIVE(1, "激活/有效", false),
    FREEZE(2, "冻结/禁用", false),
    DELETE(3, "删除", false);

    private final Integer code;
    private final String desc;
    private final boolean defaultValue;
}
