package top.easyblog.titan.service.oauth;

import top.easyblog.titan.bean.AuthenticationDetailsBean;
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

    /**
     * 登录
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean login(LoginRequest request);

    /**
     * 检查登录状态
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean checkLoginHealth(LogoutRequest request);

    /**
     * 退出
     *
     * @param request
     */
    void logout(LogoutRequest request);

    /**
     * 注册
     *
     * @param request
     * @return
     */
    AuthenticationDetailsBean register(RegisterUserRequest request);


    /**
     * 生成登录token
     *
     * @return
     */
    default String generateLoginToken() {
        return EncryptUtils.MD5(IdGenerator.getUUID() + System.currentTimeMillis());
    }
}
