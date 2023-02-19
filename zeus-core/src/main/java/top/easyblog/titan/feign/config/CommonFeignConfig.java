package top.easyblog.titan.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.response.ZeusResultCode;

/**
 * @author: frank.huang
 * @date: 2021-12-04 11:35
 */
public class CommonFeignConfig extends FeignConfig {

    @Bean
    public ErrorDecoder error() {
        return (s, response) -> {
            throw new BusinessException(ZeusResultCode.REMOTE_INVOKE_FAILED, "远程调用失败");
        };
    }

}
