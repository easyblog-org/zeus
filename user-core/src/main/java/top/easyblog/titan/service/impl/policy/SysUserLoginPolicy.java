package top.easyblog.titan.service.impl.policy;

import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.service.data.AccessAccountService;

/**
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
public class SysUserLoginPolicy implements LoginPolicy {

    private AccessAccountService accessAccountService;

    @Override
    public LoginDetailsBean doLogin(LoginRequest request) {

        return null;
    }
}
