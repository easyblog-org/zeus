package top.easyblog.titan.service.oauth;

import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;

/**
 * @author frank.huang
 * @date 2022/01/29 16:19
 */
public interface ILoginStrategy {

    /**
     * 登录策略
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean doLogin(LoginRequest request);

    /**
     * 注册策略
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean doRegister(RegisterUserRequest request);

}
