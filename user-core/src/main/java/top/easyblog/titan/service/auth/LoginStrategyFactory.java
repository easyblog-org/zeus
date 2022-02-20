package top.easyblog.titan.service.auth;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.util.ApplicationContextBeanHelper;

import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 16:27
 */
@Slf4j
public class LoginStrategyFactory {
    public static ILoginStrategy getLoginPolicy(Integer identifierType) {
        try {
            IdentifierType type = IdentifierType.subCodeOf(identifierType);
            if (Objects.isNull(type) || Objects.equals(IdentifierType.UNKNOWN, type)) {
                throw new UnsupportedOperationException(ResultCode.ERROR_LOGIN_POLICY.getCode());
            }
            String className = type.getPolicyClassName();
            return ApplicationContextBeanHelper.getBean(className, ILoginStrategy.class);
        } catch (Exception e) {
            log.error("Get login policy failed,error:{},cause:{}", e.getMessage(), e.getCause());
        }
        return null;
    }
}
