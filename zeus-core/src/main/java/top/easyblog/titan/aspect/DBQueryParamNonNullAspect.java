package top.easyblog.titan.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import top.easyblog.titan.util.PropertiesUtils;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: frank.huang
 * @date: 2023-02-25 20:38
 */
@Slf4j
@Aspect
@Component
public class DBQueryParamNonNullAspect {

    @Pointcut("@annotation(top.easyblog.titan.annotation.DBQueryParamNonNull)")
    public void checkNouNull() {
    }


    @Around("checkNouNull()")
    public Object beforeQueryCheck(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        AtomicBoolean allArgEmpty = new AtomicBoolean(true);
        Arrays.stream(args).filter(arg -> {
            return Objects.nonNull(arg) && arg.getClass().isAnnotationPresent(NotEmpty.class);
        }).forEach(arg -> {
            try {
                allArgEmpty.set(allArgEmpty.get() & PropertiesUtils.allFieldsAreNull(arg));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        Object obj = pjp.proceed(pjp.getArgs());
        return Objects.equals(allArgEmpty.get(), Boolean.TRUE) ? null : obj;
    }
}
