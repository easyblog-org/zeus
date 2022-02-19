package top.easyblog.titan.service.auth;

import top.easyblog.titan.bean.UserDetailsBean;
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
    UserDetailsBean doLogin(LoginRequest request);

    /**
     * 注册策略
     *
     * @param request
     * @return
     */
    UserDetailsBean doRegister(RegisterUserRequest request);

}
