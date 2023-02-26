package top.easyblog.titan.annotation;

import java.lang.annotation.*;

/**
 * @author: frank.huang
 * @date: 2023-02-25 20:37
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DBQueryParamNonNull {
}
