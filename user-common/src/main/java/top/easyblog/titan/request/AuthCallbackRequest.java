package top.easyblog.titan.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 第三方回调请求参数
 *
 * @author: frank.huang
 * @date: 2022-02-27 21:47
 */
@Data
public class AuthCallbackRequest implements Serializable {
    /**
     * 三方平台：Gitee、GitHub、QQ......,具体参考IdentifierType
     */
    private Integer platform;
    /**
     * 三方平台回调code
     */
    private String code;
    private String authCode;
    private String authorizationCode;
    private String state;
    private String oauthToken;
    private String oauthVerifier;
}
