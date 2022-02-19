package top.easyblog.titan.service.auth;

import top.easyblog.titan.enums.IdentifierType;

/**
 * 第三方登录认证
 *
 * @author frank.huang
 * @date 2022/01/29 14:07
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

    /**
     * 注册账户
     *
     * @param t      账户实体对象
     * @param userId 本系统用户的Id
     * @return 返回新增的数据库Id
     */
    int register(T t, int userId);

    /**
     * 根据授权的open_id和应用类型查找系统中是否有这个用户
     *
     * @param openId  open_id
     * @param appType app_type, 比如 : QQ、GitHub
     * @return T
     */
    T getUserByOpenIdAndAppType(String openId, IdentifierType appType);
}
