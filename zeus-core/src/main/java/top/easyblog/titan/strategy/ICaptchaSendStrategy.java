package top.easyblog.titan.strategy;

import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.util.IdGenerator;

import java.util.concurrent.TimeUnit;

import static top.easyblog.titan.constant.LoginConstants.*;

/**
 * 验证码发送策略
 *
 * @author: frank.huang
 * @date: 2023-02-03 21:25
 */
public interface ICaptchaSendStrategy {

    /**
     * 获取发送验证码的渠道
     *
     * @return
     * @see top.easyblog.titan.enums.CaptchaSendChannel
     */
    Integer getCaptchaSendChannel();


    /**
     * 发送验证码
     *
     * @param identifier
     */
    void sendCaptchaCode(String identifier);

}
