package top.easyblog.titan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.easyblog.titan.bean.AuthorizationBean;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.AuthCallbackRequest;
import top.easyblog.titan.request.OauthRequest;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.strategy.IOauthStrategy;
import top.easyblog.titan.strategy.OauthStrategyContext;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-02-27 18:31
 */
@Slf4j
@Service
public class OauthService {


    public AuthorizationBean authorize(OauthRequest request) {
        IOauthStrategy oauthPolicy = OauthStrategyContext.getOauthStrategy(request.getIdentifierType());
        if (Objects.isNull(oauthPolicy)) {
            throw new BusinessException(ZeusResultCode.INTERNAL_ERROR);
        }
        return oauthPolicy.authorize(request);
    }

    public AuthorizationBean callback(AuthCallbackRequest authCallback) {
        IOauthStrategy oauthPolicy = OauthStrategyContext.getOauthStrategy(authCallback.getPlatform());
        if (Objects.isNull(oauthPolicy)) {
            throw new BusinessException(ZeusResultCode.INTERNAL_ERROR);
        }
        return oauthPolicy.callback(authCallback);
    }
}
