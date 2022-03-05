package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2022-02-27 11:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryGitHubAuthUserRequest {
    private String token;
}
