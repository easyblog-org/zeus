package top.easyblog.titan.strategy.impl.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.titan.bean.AuthenticationDetailsBean;
import top.easyblog.titan.bean.AuthorizationBean;
import top.easyblog.titan.feign.client.dto.GiteeAuthDTO;
import top.easyblog.titan.feign.client.dto.GiteeAuthTokenDTO;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.feign.client.GiteeClient;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.strategy.IAuthStrategy;
import top.easyblog.titan.service.ILoginService;
import top.easyblog.titan.strategy.IOauthStrategy;
import top.easyblog.titan.strategy.impl.auth.config.GiteeAuthProperties;
import top.easyblog.titan.util.JsonUtils;

import java.util.Optional;

/**
 * Gitee第三方接入官方文档：https://gitee.com/api/v5/oauth_doc#/list-item-1
 *
 * @author: frank.huang
 * @date: 2022-03-06 00:33
 */
@Slf4j
@Component
public class GiteeAuthStrategyImpl implements IAuthStrategy<GiteeAuthDTO>, IOauthStrategy {


    @Autowired
    private GiteeClient giteeClient;

    @Autowired
    private GiteeAuthProperties giteeAuthProperties;

    @Autowired
    private ILoginService loginService;


    @Override
    public Integer getIdentifierType() {
        return IdentifierType.GITEE.getSubCode();
    }

    @Override
    public String getAccessToken(String code) {
        GiteeAuthTokenDTO accessToken = giteeClient.getAccessToken(QueryGiteeAuthTokenRequest.builder()
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
        }).orElseThrow(() -> new BusinessException(ZeusResultCode.REQUEST_GITEE_ACCESS_TOKEN_FAILED));
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
    public GiteeAuthDTO getUserInfo(String accessToken) {
        GiteeAuthDTO giteeUserInfo = giteeClient.getGiteeUserInfo(accessToken);
        return Optional.of(giteeUserInfo).map(item -> {
            log.info("Get gitee user info: {}", JsonUtils.toJSONString(item));
            return item;
        }).orElseThrow(() -> new BusinessException(ZeusResultCode.REQUEST_GITEE_USER_INFO_FAILED));
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
            GiteeAuthDTO userInfo = getUserInfo(token);
            AuthenticationDetailsBean authenticationDetailsBean = loginService.register(RegisterUserRequest.builder()
                    .identifierType(IdentifierType.GITEE.getSubCode())
                    .identifier(userInfo.getId())
                    .headerImg(CreateUserHeaderImgRequest.builder().headerImgUrl(userInfo.getAvatarUrl()).build())
                    .build());
            return AuthorizationBean.builder().user(authenticationDetailsBean.getUser()).build();
        }).orElseThrow(() -> new BusinessException(ZeusResultCode.REQUEST_GITEE_ACCESS_TOKEN_FAILED));
    }
}
