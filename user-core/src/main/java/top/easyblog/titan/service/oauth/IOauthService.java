package top.easyblog.titan.service.oauth;

import top.easyblog.titan.bean.AuthorizationBean;
import top.easyblog.titan.request.AuthCallbackRequest;
import top.easyblog.titan.request.OauthRequest;

/**
 * @author: frank.huang
 * @date: 2022-02-27 18:32
 */
public interface IOauthService {

    /**
     * 第三方登录获取 AccessToken
     *
     * @param request
     * @return
     */
    AuthorizationBean authorize(OauthRequest request);

    /**
     * 第三方授权回调
     *
     * @param callback
     * @return
     */
    AuthorizationBean callback(AuthCallbackRequest callback);
}
