package top.easyblog.titan.strategy;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.response.ZeusResultCode;

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
public class CaptchaSendStrategyContext {

    private static Map<Integer, ICaptchaSendStrategy> captchaSendStrategyMap = Maps.newHashMap();

    @Autowired
    private List<ICaptchaSendStrategy> captchaSendStrategies;

    public static ICaptchaSendStrategy getCaptchaSendStrategy(Integer channel) {
        ICaptchaSendStrategy captchaSendStrategy = captchaSendStrategyMap.get(channel);
        IdentifierType type = IdentifierType.subCodeOf(channel);
        if (Objects.isNull(type) || Objects.equals(IdentifierType.UNKNOWN, type)) {
            throw new UnsupportedOperationException(ZeusResultCode.ERROR_CAPTCHA_CODE_SEND_STRATEGY.getCode());
        }
        return captchaSendStrategy;
    }

    @PostConstruct
    public void initCaptchaSendStrategyMap() {
        log.info("Start init captcha strategy.....");
        captchaSendStrategyMap = captchaSendStrategies.stream().collect(Collectors.toMap(ICaptchaSendStrategy::getCaptchaSendChannel, Function.identity(), (x, y) -> x));
        log.info("Init login captcha successfully!");
    }
}
