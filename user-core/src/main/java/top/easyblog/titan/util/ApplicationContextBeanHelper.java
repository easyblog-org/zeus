package top.easyblog.titan.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * @author frank.huang
 * @date 2022/01/30 09:48
 */
@Component
public class ApplicationContextBeanHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextBeanHelper.applicationContext = applicationContext;
    }

    /**
     * 根据类名获取IOC容器中目标类型的bean实例
     *
     * @param beanClassName
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanClassName, Class<T> requiredType) {
        if (beanClassName == null || beanClassName.length() <= 0) {
            throw new IllegalArgumentException("className为空");
        }

        String shortClassName = ClassUtils.getShortName(beanClassName);
        String beanName = Introspector.decapitalize(shortClassName);
        return applicationContext != null ? applicationContext.getBean(beanName, requiredType) : null;
    }
}
