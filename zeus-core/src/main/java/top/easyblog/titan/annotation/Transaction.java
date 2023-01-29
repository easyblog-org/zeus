package top.easyblog.titan.annotation;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @author frank.huang
 * @since 2021/8/21 18:18
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public @interface Transaction {
}
