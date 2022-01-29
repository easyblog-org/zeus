package top.easyblog.titan.service;

import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统登录认证
 *
 * @author frank.huang
 * @date 2022/01/29 14:04
 */
public interface ILoginService {

    LoginDetailsBean login(LoginRequest request, HttpServletRequest httpServletRequest);

    UserDetailsBean checkLoginHealth(String token);

    void logout(String token);

    void register(RegisterUserRequest request);

}
