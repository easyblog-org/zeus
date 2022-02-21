package top.easyblog.titan.service.oauth;

/**
 * 三方平台OAuth2.0认证
 *
 * @author frank.huang
 * @date 2022/02/21 16:14
 */
public interface IAuthService<T> {
    /**
     * 根据code获得Token
     *
     * @param code code
     * @return token
     */
    String getAccessToken(String code);

    /**
     * 根据Token获得OpenId
     *
     * @param accessToken Token
     * @return openId
     */
    String getOpenId(String accessToken);

    /**
     * 刷新Token
     *
     * @param code code
     * @return 新的token
     */
    String refreshToken(String code);

    /**
     * 拼接授权URL
     *
     * @return URL
     */
    String getAuthorizationUrl();

    /**
     * 根据Token和OpenId获得用户信息
     *
     * @param accessToken Token
     * @return 第三方应用给的用户信息
     */
    T getUserInfo(String accessToken);
}
