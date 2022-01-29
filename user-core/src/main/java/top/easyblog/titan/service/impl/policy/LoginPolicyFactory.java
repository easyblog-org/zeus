package top.easyblog.titan.service.impl.policy;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.titan.enums.IdentifierType;

/**
 * @author frank.huang
 * @date 2022/01/29 16:27
 */
@Slf4j
public class LoginPolicyFactory {
    public static LoginPolicy getLoginPolicy(Byte identifierType) {
        try {
            String className = IdentifierType.codeOf(identifierType).getPolicyClassName();
            return (LoginPolicy) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            log.error("Get login policy failed,error:{},cause:{}", e.getMessage(), e.getCause());
        }
        return null;
    }
}
