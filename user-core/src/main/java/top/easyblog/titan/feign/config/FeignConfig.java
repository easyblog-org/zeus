package top.easyblog.titan.feign.config;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.feign.config.http.encoder.CamelToUnderscoreEncoder;
import top.easyblog.titan.feign.internal.FeignLogger;
import top.easyblog.titan.feign.internal.OkHttpClientFactory;
import top.easyblog.titan.feign.sign.CommonSignInterceptor;
import top.easyblog.titan.response.ResultCode;
import top.easyboot.sign.SignHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: frank.huang
 * @date: 2021-11-14 20:31
 */
@Configuration
public class FeignConfig {

    @Autowired
    protected GsonHttpMessageConverter customGsonConverters;

    @Autowired
    protected SignHandler signHandler;

    @Value("${feign.custom.read-timeout:6000}")
    private int readTimeout;

    @Value("${feign.custom.write-timeout:5000}")
    private int writeTimeout;

    @Value("${feign.custom.connect-timeout:3000}")
    private int connectTimeout;

    @Value("${feign.custom.period:100}")
    private int period;

    @Value("${feign.custom.retry-max-period:1000}")
    private int retryMaxPeriod;

    @Value("${feign.custom.retry-max-attempts:3}")
    private int retryMaxAttempts;

    @Value("${feign.excludes}")
    private String excludesPath;

    @Bean
    public Request.Options options() {
        return new Request.Options(readTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, true);
    }

    @Bean
    public Client client() {
        return new OkHttpClient(OkHttpClientFactory.getInstance(connectTimeout, writeTimeout, readTimeout));
    }

    @Bean
    public RequestInterceptor requestHeader() {
        return requestTemplate -> requestTemplate.header(Constants.HEADER_REQUEST_ID, MDC.get(Constants.REQUEST_ID));
    }


    @Bean
    public Retryer retryer() {
        return new Retryer.Default(period, retryMaxPeriod, retryMaxAttempts);
    }

    @Bean
    public ErrorDecoder error() {
        return (method, response) -> {
            throw new BusinessException(ResultCode.REMOTE_INVOKE_FAIL, response.reason());
        };
    }

    @Bean
    public Decoder decoder() {
        return new ResponseEntityDecoder(new SpringDecoder(() -> new HttpMessageConverters(false, Lists.newArrayList(customGsonConverters))));
    }

    @Bean
    public Encoder encoder() {
        return new SpringEncoder(() -> new HttpMessageConverters(false, Lists.newArrayList(customGsonConverters)));
    }

    @Bean
    public RequestInterceptor sign() {
        Set<String> excludes = new HashSet<>(Splitter.on(excludesPath).omitEmptyStrings().splitToList(","));
        return new CommonSignInterceptor(signHandler, excludes);
    }

    @Bean
    public QueryMapEncoder queryMapEncoder() {
        return new CamelToUnderscoreEncoder();
    }

    @Bean
    public Logger.Level logger() {
        return Logger.Level.FULL;
    }

    @Bean
    public FeignLoggerFactory feignLoggerFactory(@Value("spring.profiles.active:dev") String env) {
        return type -> new FeignLogger(type, env);
    }

}
