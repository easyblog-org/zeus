package top.easyblog.titan.service.oauth.impl.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.titan.bean.auth.GitHubAuthBean;
import top.easyblog.titan.service.oauth.IAuthService;
import top.easyblog.titan.service.oauth.ILoginService;
import top.easyblog.titan.service.oauth.impl.auth.config.GitHubAuthProperties;

/**
 * @author frank.huang
 * @date 2022/02/21 16:18
 */
@Service
public class GitHubAuthServiceImpl implements IAuthService<GitHubAuthBean> {

    @Autowired
    private GitHubAuthProperties gitHubAuthProperties;

    @Autowired
    private ILoginService loginService;

    @Override

    public String getAccessToken(String code) {
        return null;
    }

    @Override
    public String getOpenId(String accessToken) {
        return null;
    }

    @Override
    public String refreshToken(String code) {
        return null;
    }

    @Override
    public String getAuthorizationUrl() {
        return null;
    }

    @Override
    public GitHubAuthBean getUserInfo(String accessToken) {
        return null;
    }
}
