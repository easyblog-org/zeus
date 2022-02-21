package top.easyblog.titan.service.oauth.impl.auth.config;

import lombok.Data;

/**
 * @author frank.huang
 * @date 2022/02/21 16:34
 */
@Data
public abstract class AbstractAuthProperties {
    protected String clientId;
    protected String clientSecret;
    protected String authorizeUrl;
    protected String redirectUrl;
    protected String accessTokenUrl;
    protected String userInfoUrl;
}
