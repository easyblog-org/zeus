package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2022-02-27 11:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryGitHubAuthTokenRequest {
    private String clientId;
    private String clientSecret;
    private String code;
    private String grantType;
    private String state;
    private String redirectUri;
}
