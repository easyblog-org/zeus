package top.easyblog.titan.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-01-29 21:29
 */
@Data
@Builder
public class RegisterDetailsBean {
    private UserDetailsBean user;
}
