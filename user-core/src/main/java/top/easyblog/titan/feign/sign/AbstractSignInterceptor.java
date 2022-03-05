package top.easyblog.titan.feign.sign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import top.easyboot.sign.SignEntity;
import top.easyboot.sign.SignHandler;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: frank.huang
 * @date: 2021-12-01 20:36
 */
public abstract class AbstractSignInterceptor implements RequestInterceptor {

    protected final SignHandler signHandler;

    public AbstractSignInterceptor(SignHandler signHandler) {
        this.signHandler = signHandler;
    }

    /**
     * 是否支持验签，针对特殊路径可以不计算验签
     *
     * @param requestTemplate
     * @return
     */
    protected abstract boolean support(RequestTemplate requestTemplate);

    /**
     * 验签计算之前前置操作
     *
     * @param requestTemplate
     */
    protected abstract SignEntity beforeSign(RequestTemplate requestTemplate);

    /**
     * 验签计算之后后置操作
     *
     * @param requestTemplate
     */
    protected abstract void afterSign(RequestTemplate requestTemplate, String sign) throws InvocationTargetException, IllegalAccessException;

    @SneakyThrows
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (!support(requestTemplate)) {
            return;
        }
        SignEntity signEntity = beforeSign(requestTemplate);
        String sign = signHandler.generateRequestSign(signEntity);
        afterSign(requestTemplate, sign);
    }
}
