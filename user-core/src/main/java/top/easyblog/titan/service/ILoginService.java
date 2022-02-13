package top.easyblog.titan.service;

import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.LogoutRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.util.EncryptUtils;
import top.easyblog.titan.util.IdGenerator;

/**
 * 系统登录认证
 *
 * @author frank.huang
 * @date 2022/01/29 14:04
 */
public interface ILoginService {

    LoginDetailsBean login(LoginRequest request);

    UserDetailsBean checkLoginHealth(String token);

    void logout(LogoutRequest request);

    UserDetailsBean register(RegisterUserRequest request);

    /**
     * 生成登录token
     *
     * @return
     */
    default String generateLoginToken() {
        return EncryptUtils.MD5(IdGenerator.getUUID() + System.currentTimeMillis());
    }
}
