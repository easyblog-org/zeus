package top.easyblog.titan.strategy.impl.captcha;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.RedisService;
import top.easyblog.titan.strategy.ICaptchaSendStrategy;
import top.easyblog.titan.util.IdGenerator;

import java.util.concurrent.TimeUnit;

import static top.easyblog.titan.constant.LoginConstants.*;

/**
 * @author: frank.huang
 * @date: 2023-02-03 22:18
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractCaptchaSendStrategy implements ICaptchaSendStrategy {

    private RedisService redisService;

    protected String generateCaptchaCode(String identifier) {
        String captchaCode = IdGenerator.generateCaptchaCode(DEFAULT_CAPTCHA_CODE_LENGTH);
        boolean ok = redisService.set(String.format(PHONE_LOGIN_CAPTCHA_CODE, identifier), captchaCode, PHONE_LOGIN_CAPTCHA_CODE_EXPIRE, TimeUnit.MINUTES);
        if (!ok) {
            throw new BusinessException(ZeusResultCode.SEND_CAPTCHA_FAILED);
        }
        log.info("Generate captcha code {} and cache in redis successfully!", captchaCode);
        return captchaCode;
    }

}
