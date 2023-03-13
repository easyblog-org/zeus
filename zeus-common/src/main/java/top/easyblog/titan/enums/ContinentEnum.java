package top.easyblog.titan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 洲枚举
 *
 * @author: frank.huang
 * @date: 2023-03-12 15:39
 */
@Getter
@AllArgsConstructor
public enum ContinentEnum {
    AFRICA("AF", "Africa"),
    ANTARCTICA("AN", "Antarctica"),
    ASIA("AS", "Asia"),
    AUSTRALIA("AU", "Australia"),
    EUROPE("EU", "Europe"),
    NORTH_AMERICA("NA", "North America"),
    SOUTH_AMERICA("SA", "South America");

    private final String code;
    private final String desc;

    public static ContinentEnum codeOf(String code) {
        if (StringUtils.isBlank(code)) return null;
        return Arrays.stream(ContinentEnum.values()).filter(item -> StringUtils.equalsIgnoreCase(item.getCode(), code)).findAny().orElse(null);
    }

}
