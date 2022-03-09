package top.easyblog.titan.service.oauth.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.easyblog.titan.bean.AuthorizationBean;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.AuthCallbackRequest;
import top.easyblog.titan.request.OauthRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.oauth.IOauthService;
import top.easyblog.titan.service.oauth.OauthStrategyFactory;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-02-27 18:31
 */
@Slf4j
@Service
public class OauthService {


    public AuthorizationBean authorize(OauthRequest request) {
        IOauthService oauthPolicy = OauthStrategyFactory.getOauthPolicy(request.getIdentifierType());
        if (Objects.isNull(oauthPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        return oauthPolicy.authorize(request);
    }

    public AuthorizationBean callback(AuthCallbackRequest authCallback) {
        IOauthService oauthPolicy = OauthStrategyFactory.getOauthPolicy(authCallback.getPlatform());
        if (Objects.isNull(oauthPolicy)) {
            throw new BusinessException(ResultCode.INTERNAL_ERROR);
        }
        return oauthPolicy.callback(authCallback);
    }
}
