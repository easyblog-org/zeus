package top.easyblog.titan.feign.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.easyboot.sign.SignHandler;
import top.easyblog.titan.feign.sign.FeignCommonInterceptor;

/**
 * @author: frank.huang
 * @date: 2021-12-04 11:35
 */
@Configuration
public class CommonFeignConfiguration extends FeignConfiguration {

    @Autowired
    private SignHandler signHandler;

    @Bean
    public RequestInterceptor sign() {
        return new FeignCommonInterceptor(signHandler);
    }
}
