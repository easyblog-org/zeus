package top.easyblog.titan.service.oauth;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.util.ApplicationContextBeanHelper;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-02-27 18:37
 */
@Slf4j
public class OauthStrategyFactory {
    public static IOauthService getOauthPolicy(Integer identifierType) {
        try {
            IdentifierType type = IdentifierType.subCodeOf(identifierType);
            if (Objects.isNull(type) || Objects.equals(IdentifierType.UNKNOWN, type)) {
                throw new UnsupportedOperationException(ResultCode.ERROR_OAUTH_POLICY.getCode());
            }
            String authServiceName = type.getAuthServiceName();
            return ApplicationContextBeanHelper.getBeanByClassName(authServiceName, IOauthService.class);
        } catch (Exception e) {
            log.error("Get oauth policy failed,error:{},cause:{}", e.getMessage(), e.getCause());
        }
        return null;
    }
}
