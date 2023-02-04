package top.easyblog.titan.strategy.impl.captcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.titan.enums.CaptchaSendChannel;
import top.easyblog.titan.feign.client.NestorClient;
import top.easyblog.titan.service.RedisService;
import top.easyblog.titan.strategy.ICaptchaSendStrategy;

/**
 * @author: frank.huang
 * @date: 2023-02-03 21:57
 */
@Slf4j
@Component
public class EmailCaptchaStrategy extends AbstractCaptchaSendStrategy {

    @Autowired
    private NestorClient nestorClient;

    public EmailCaptchaStrategy(RedisService redisService) {
        super(redisService);
    }

    @Override
    public Integer getCaptchaSendChannel() {
        return CaptchaSendChannel.EMAIL.getCode();
    }

    @Override
    public void sendCaptchaCode(String identifier) {

    }
}
