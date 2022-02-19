package top.easyblog.titan.service.auth.policy;

import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;

/**
 * @author frank.huang
 * @date 2022/01/29 16:19
 */
public interface LoginStrategy {

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
