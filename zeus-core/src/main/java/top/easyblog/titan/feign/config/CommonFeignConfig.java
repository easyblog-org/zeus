package top.easyblog.titan.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.response.ResultCode;

/**
 * @author: frank.huang
 * @date: 2021-12-04 11:35
 */
public class CommonFeignConfig extends FeignConfig {

    @Bean
    public ErrorDecoder error() {
        return (s, response) -> {
            throw new BusinessException(ResultCode.REMOTE_INVOKE_FAIL, "远程调用失败");
        };
    }

}
