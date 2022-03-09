package top.easyblog.titan.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2022-02-20 22:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDetailsBean {
    protected UserDetailsBean user;
}
