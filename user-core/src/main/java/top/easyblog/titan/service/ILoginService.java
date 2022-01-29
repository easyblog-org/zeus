package top.easyblog.titan.service;

import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.LoginRequest;

/**
 * 系统登录认证
 *
 * @author frank.huang
 * @date 2022/01/29 14:04
 */
public interface ILoginService {

    LoginDetailsBean login(LoginRequest request);

    void logout(String token);

    void register(CreateUserRequest request);

}
