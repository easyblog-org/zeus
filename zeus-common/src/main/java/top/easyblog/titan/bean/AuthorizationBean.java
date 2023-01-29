package top.easyblog.titan.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-02-27 15:08
 */
@Data
@Builder
public class AuthorizationBean {
    private String authorizationUrl;
    private UserDetailsBean user;
}
