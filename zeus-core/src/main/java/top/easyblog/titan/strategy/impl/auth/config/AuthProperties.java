package top.easyblog.titan.strategy.impl.auth.config;

import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-03-06 11:59
 */
@Data
public class AuthProperties {
    private String clientId;
    private String clientSecret;
    private String authorizeUrl;
    protected String redirectUrl;
}
