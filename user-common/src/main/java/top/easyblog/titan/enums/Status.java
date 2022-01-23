package top.easyblog.titan.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author: frank.huang
 * @date: 2021-10-31 23:40
 */
@Getter
@AllArgsConstructor
public enum Status {

    UNKNOWN(-1),

    ENABLE(1),

    DISABLE(0);

    private final Integer code;

    public Status of(Integer code){
       return Arrays.stream(Status.values()).filter(c -> c.getCode().equals(code)).findAny().orElse(null);
    }

}
