package top.easyblog.titan.service.impl.policy;

import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.request.LoginRequest;

/**
 * @author frank.huang
 * @date 2022/01/29 16:19
 */
@FunctionalInterface
public interface LoginPolicy {
    /**
     * 登录策略
     *
     * @param request
     * @return
     */
    LoginDetailsBean doLogin(LoginRequest request);

}
