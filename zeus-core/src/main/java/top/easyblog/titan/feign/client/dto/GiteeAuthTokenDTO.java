package top.easyblog.titan.feign.client.dto;

import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-03-06 12:27
 */
@Data
public class GiteeAuthTokenDTO {
    private String accessToken;
    private String tokenType;
    private Long expireIn;
    private String scope;
}
