package top.easyblog.titan.aspect;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.easyblog.titan.annotation.RequestParamAlias;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 请求参数下划线参数自动转驼峰参数处理器
 *
 * @author: frank.huang
 * @date: 2022-02-12 17:10
 */
@Slf4j
public class UnderlineToHumpArgumentResolver extends AbstractCustomizeArgumentResolver {
    /**
     * 匹配_加任意一个字符
     */
    private static final Pattern UNDER_LINE_PATTERN = Pattern.compile("_(\\w)");

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestParamAlias.class);
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object obj = handleParameterNames(methodParameter, nativeWebRequest);
        valid(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory, obj);
        return obj;
    }

    /**
     * 处理参数
     *
     * @param parameter
     * @param webRequest
     * @return
     */
    private Object handleParameterNames(MethodParameter parameter, NativeWebRequest webRequest) {
        Object obj = BeanUtils.instantiateClass(parameter.getParameterType());
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Iterator<String> paramNames = webRequest.getParameterNames();
        while (paramNames.hasNext()) {
            String originalParam = paramNames.next();
            String formattedParam = underLineToCamel(originalParam);
            if (wrapper.isWritableProperty(formattedParam)) {
                Object value = webRequest.getParameter(originalParam);
                log.debug("Handle request param underline to camel ==> {}={}", originalParam, value);
                wrapper.setPropertyValue(formattedParam, value);
            }
        }
        return obj;
    }

    /**
     * 下换线转驼峰
     *
     * @param source
     * @return
     */
    private String underLineToCamel(String source) {
        Matcher matcher = UNDER_LINE_PATTERN.matcher(source);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
