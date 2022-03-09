package top.easyblog.titan.service.oauth.impl.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: frank.huang
 * @date: 2022-03-06 12:17
 */
@Component
@ConfigurationProperties(prefix = "oauth.gitee")
public class GiteeAuthProperties extends AuthProperties {
}
