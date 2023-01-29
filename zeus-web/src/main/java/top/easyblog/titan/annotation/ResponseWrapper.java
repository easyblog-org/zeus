package top.easyblog.titan.annotation;

import java.lang.annotation.*;

/**
 * 响应体包装
 * @author: frank.huang
 * @date: 2021-11-01 18:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResponseWrapper {
}
