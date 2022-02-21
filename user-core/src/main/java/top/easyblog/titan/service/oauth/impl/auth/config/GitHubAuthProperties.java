package top.easyblog.titan.service.oauth.impl.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author frank.huang
 * @date 2022/02/21 16:50
 */
@Data
@Component
@ConfigurationProperties(prefix = "oauth.github")
public class GitHubAuthProperties {
}
