package top.easyblog.titan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.easyboot.handler.SignInterceptor;
import top.easyblog.titan.handler.LogInterceptor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2021-11-06 13:08
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private SignInterceptor signInterceptor;

    @Autowired
    private GsonHttpMessageConverter customConverters;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(signInterceptor);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, customConverters);
    }

}
