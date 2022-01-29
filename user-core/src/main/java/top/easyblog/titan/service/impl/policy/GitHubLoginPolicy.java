package top.easyblog.titan.service.impl.policy;

import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.request.LoginRequest;

/**
 * @author frank.huang
 * @date 2022/01/29 16:23
 */
public class GitHubLoginPolicy implements LoginPolicy {
    @Override
    public LoginDetailsBean doLogin(LoginRequest request) {
        return null;
    }
}
