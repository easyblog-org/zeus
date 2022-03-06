package top.easyblog.titan.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-03-06 12:30
 */
@Data
@Builder
public class QueryGiteeAuthTokenRequest {
    private String grantType;
    private String code;
    private String clientId;
    private String redirectUri;
    private String clientSecret;
}
