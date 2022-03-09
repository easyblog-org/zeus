package top.easyblog.titan.service.oauth.impl.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.AuthorizationBean;
import top.easyblog.titan.bean.auth.GiteeAuthBean;
import top.easyblog.titan.bean.auth.GiteeAuthTokenBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.feign.client.GiteeClient;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.oauth.IAuthService;
import top.easyblog.titan.service.oauth.ILoginService;
import top.easyblog.titan.service.oauth.IOauthService;
import top.easyblog.titan.service.oauth.impl.auth.config.GiteeAuthProperties;
import top.easyblog.titan.util.JsonUtils;

import java.util.Optional;

/**
 * Gitee第三方接入官方文档：https://gitee.com/api/v5/oauth_doc#/list-item-1
 *
 * @author: frank.huang
 * @date: 2022-03-06 00:33
 */
@Slf4j
@Service
public class GiteeAuthServiceImpl implements IAuthService<GiteeAuthBean>, IOauthService {


    @Autowired
    private GiteeClient giteeClient;

    @Autowired
    private GiteeAuthProperties giteeAuthProperties;

    @Autowired
    private ILoginService loginService;

    @Override
    public String getAccessToken(String code) {
        GiteeAuthTokenBean accessToken = giteeClient.getAccessToken(QueryGiteeAuthTokenRequest.builder()
                .clientId(giteeAuthProperties.getClientId())
                .clientSecret(giteeAuthProperties.getClientSecret())
                .code(code)
                .grantType(LoginConstants.COMMON_GRANT_TYPE)
                .redirectUri(giteeAuthProperties.getRedirectUrl())
                .build());
        return Optional.ofNullable(accessToken).map(item -> {
            String token = item.getAccessToken();
            log.info("Get Gitee access_token: {}", JsonUtils.toJSONString(accessToken));
            return token;
        }).orElseThrow(() -> new BusinessException(ResultCode.REQUEST_GITEE_ACCESS_TOKEN_FAILED));
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
    public GiteeAuthBean getUserInfo(String accessToken) {
        GiteeAuthBean giteeUserInfo = giteeClient.getGiteeUserInfo(accessToken);
        return Optional.of(giteeUserInfo).map(item -> {
            log.info("Get gitee user info: {}", JsonUtils.toJSONString(item));
            return item;
        }).orElseThrow(() -> new BusinessException(ResultCode.REQUEST_GITEE_USER_INFO_FAILED));
    }

    @Override
    public AuthorizationBean authorize(OauthRequest request) {
        String authorizationUr = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code", giteeAuthProperties.getAuthorizeUrl(),
                giteeAuthProperties.getClientId(), giteeAuthProperties.getRedirectUrl());
        return AuthorizationBean.builder().authorizationUrl(authorizationUr).build();
    }

    @Override
    public AuthorizationBean callback(AuthCallbackRequest callback) {
        String accessToken = getAccessToken(callback.getCode());
        return Optional.ofNullable(accessToken).map(token -> {
            GiteeAuthBean userInfo = getUserInfo(token);
            AuthenticationDetailsBean authenticationDetailsBean = loginService.register(RegisterUserRequest.builder()
                    .identifierType(IdentifierType.GITEE.getSubCode())
                    .identifier(userInfo.getId())
                    .headerImg(CreateUserHeaderImgRequest.builder().headerImgUrl(userInfo.getAvatarUrl()).build())
                    .build());
            return AuthorizationBean.builder().user(authenticationDetailsBean.getUser()).build();
        }).orElseThrow(() -> new BusinessException(ResultCode.REQUEST_GITEE_ACCESS_TOKEN_FAILED));
    }
}
