package top.easyblog.titan.strategy.impl.captcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.titan.enums.CaptchaSendChannel;
import top.easyblog.titan.feign.client.NestorClient;
import top.easyblog.titan.service.RedisService;
import top.easyblog.titan.strategy.ICaptchaSendStrategy;

/**
 * 手机短信发送验证码策略
 *
 * @author: frank.huang
 * @date: 2023-02-03 21:32
 */
@Slf4j
@Component
public class SMSCaptchaStrategy extends AbstractCaptchaSendStrategy{

    @Autowired
    private NestorClient nestorClient;

    public SMSCaptchaStrategy(RedisService redisService) {
        super(redisService);
    }

    @Override
    public Integer getCaptchaSendChannel() {
        return CaptchaSendChannel.PHONE_SMS.getCode();
    }

    @Override
    public void sendCaptchaCode(String identifier) {
        String captchaCode = generateCaptchaCode(identifier);

    }
}
