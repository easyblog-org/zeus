package top.easyblog.titan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.easyblog.titan.annotation.RequestParamAlias;
import top.easyblog.titan.annotation.ResponseWrapper;
import top.easyblog.titan.bean.AuthorizationBean;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.AuthCallbackRequest;
import top.easyblog.titan.request.OauthRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.oauth.impl.OauthService;

import javax.validation.Valid;

/**
 * 第三方登录控制器
 *
 * @author: frank.huang
 * @date: 2022-02-27 14:51
 */
@RestController
@RequestMapping("/v1/open")
public class OauthController {

    @Autowired
    private OauthService oauthService;

    @ResponseWrapper
    @GetMapping(value = "/authorize")
    public AuthorizationBean authorize(@RequestParamAlias @Valid OauthRequest request) {
        return oauthService.authorize(request);
    }

    @ResponseWrapper
    @GetMapping(value = "/callback/{platform}")
    public AuthorizationBean callback(@PathVariable("platform") Integer platform, @RequestParamAlias @Valid AuthCallbackRequest callback) {
        if (IdentifierType.SYSTEM_IDENTITY_TYPE.contains(IdentifierType.codeOf(callback.getPlatform()))) {
            throw new BusinessException(ResultCode.INVALID_IDENTITY_TYPE);
        }
        callback.setPlatform(platform);
        return oauthService.callback(callback);
    }

}
