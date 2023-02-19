package top.easyblog.titan.strategy;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.util.ApplicationContextBeanHelper;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/29 16:27
 */
@Slf4j
@Component
public class LoginStrategyContext {

    private static Map<Integer, ILoginStrategy> loginStrategyMap = Maps.newHashMap();

    @Autowired
    private List<ILoginStrategy> loginStrategies;

    public static ILoginStrategy getIdentifyStrategy(Integer identifierType) {
        ILoginStrategy iLoginStrategy = loginStrategyMap.get(identifierType);
        IdentifierType type = IdentifierType.subCodeOf(identifierType);
        if (Objects.isNull(type) || Objects.equals(IdentifierType.UNKNOWN, type)) {
            throw new UnsupportedOperationException(ZeusResultCode.ERROR_LOGIN_POLICY.getCode());
        }
        return iLoginStrategy;
    }

    @PostConstruct
    public void initLoginStrategyMap() {
        log.info("Start init login strategy.....");
        loginStrategyMap = loginStrategies.stream().collect(Collectors.toMap(ILoginStrategy::getIdentifierType, Function.identity(), (x, y) -> x));
        log.info("Init login strategy successfully!");
    }
}
