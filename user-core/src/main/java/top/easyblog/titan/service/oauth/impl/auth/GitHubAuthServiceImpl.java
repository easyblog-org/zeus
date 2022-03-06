package top.easyblog.titan.service.oauth.impl.auth;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.AuthorizationBean;
import top.easyblog.titan.bean.auth.GitHubAuthBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.feign.client.GitHubClient;
import top.easyblog.titan.feign.client.GitHubOpenApiClient;
import top.easyblog.titan.request.AuthCallbackRequest;
import top.easyblog.titan.request.OauthRequest;
import top.easyblog.titan.request.QueryGitHubAuthTokenRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.oauth.IAuthService;
import top.easyblog.titan.service.oauth.ILoginService;
import top.easyblog.titan.service.oauth.IOauthService;
import top.easyblog.titan.service.oauth.impl.auth.config.GitHubAuthProperties;
import top.easyblog.titan.util.JsonUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * GitHub第三方接入官方文档：https://developer.github.com/apps/building-github-apps/identifying-and-authorizing-users-for-github-apps/
 *
 * @author frank.huang
 * @date 2022/02/21 16:18
 */
@Slf4j
@Service
public class GitHubAuthServiceImpl implements IAuthService<GitHubAuthBean>, IOauthService {

    @Autowired
    private GitHubAuthProperties gitHubAuthProperties;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private GitHubOpenApiClient gitHubOpenApiClient;

    @Override
    public String getAccessToken(String code) {
        MultiValueMap<String, String> accessToken = gitHubClient.getAccessToken(QueryGitHubAuthTokenRequest.builder()
                .clientId(gitHubAuthProperties.getClientId())
                .clientSecret(gitHubAuthProperties.getClientSecret())
                .code(code)
                .grantType(LoginConstants.COMMON_GRANT_TYPE)
                .build());
        return Optional.ofNullable(accessToken).map(item -> {
            String token = Iterables.getFirst(item.get("accessToken"), null);
            log.info("Get GitHub access_token: {}", JsonUtils.toJSONString(accessToken));
            return token;
        }).orElseThrow(() -> new BusinessException(ResultCode.REQUEST_GITHUB_ACCESS_TOKEN_FAILED));
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
    public GitHubAuthBean getUserInfo(String accessToken) {
        GitHubAuthBean gitHubAuthBean = gitHubOpenApiClient.getGithubUserInfo(String.format("token %s", accessToken));
        if (Objects.isNull(gitHubAuthBean)) {
            throw new BusinessException(ResultCode.REQUEST_GITHUB_USER_INFO_FAILED);
        }
        log.info("Get github user information: {}", JsonUtils.toJSONString(gitHubAuthBean));
        return gitHubAuthBean;
    }


    @Override
    public AuthorizationBean authorize(OauthRequest request) {
        String authorizationUr = String.format("%s?client_id=%s&state=STATE&redirect_uri=%s", gitHubAuthProperties.getAuthorizeUrl(),
                gitHubAuthProperties.getClientId(), gitHubAuthProperties.getRedirectUrl());
        return AuthorizationBean.builder().authorizationUrl(authorizationUr).build();
    }

    @Override
    public AuthorizationBean callback(AuthCallbackRequest callback) {
        String accessToken = getAccessToken(callback.getCode());
        return Optional.ofNullable(accessToken).map(token -> {
            GitHubAuthBean userInfo = getUserInfo(token);
            AuthenticationDetailsBean authenticationDetailsBean = loginService.register(RegisterUserRequest.builder()
                    .identifierType(IdentifierType.GITHUB.getSubCode())
                    .identifier(userInfo.getId())
                    .build());
            return AuthorizationBean.builder().user(authenticationDetailsBean.getUser()).build();
        }).orElseThrow(() -> new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS));
    }
}
