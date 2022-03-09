package top.easyblog.titan.feign.config;

import com.google.common.collect.Lists;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.feign.config.http.converter.FormHttpMessageConverter;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.util.JsonUtils;

/**
 * @author: frank.huang
 * @date: 2022-03-02 20:42
 */
public class CommonFormFeignConfig extends FeignConfig {

    private GsonHttpMessageConverter customGsonConverters;
    private FormHttpMessageConverter formHttpMessageConverter;


    public CommonFormFeignConfig() {
        customGsonConverters = new GsonHttpMessageConverter();
        customGsonConverters.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM, new MediaType("application", "*+json")));
        customGsonConverters.setGson(JsonUtils.getGson());

        formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_FORM_URLENCODED));
    }


    @Bean
    public Decoder decoder() {
        return new ResponseEntityDecoder(new SpringDecoder(() -> new HttpMessageConverters(false, Lists.newArrayList(customGsonConverters, formHttpMessageConverter))));
    }

    @Bean
    public Encoder encoder() {
        return new SpringEncoder(() -> new HttpMessageConverters(false, Lists.newArrayList(customGsonConverters, formHttpMessageConverter)));
    }

    @Bean
    public ErrorDecoder error() {
        return (s, response) -> {
            throw new BusinessException(ResultCode.REMOTE_INVOKE_FAIL, "远程调用失败");
        };
    }


}
