package top.easyblog.titan.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import top.easyblog.titan.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web请求日志记录拦截器
 *
 * @author: frank.huang
 * @date: 2021-11-20 19:58
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Value("${spring.custom.enable-logging-request-details:true}")
    private Boolean enableLoggingRequestDetails;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String params;
        if (enableLoggingRequestDetails) {
            params = JsonUtils.toJSONString(request.getParameterMap());
        } else {
            params = request.getParameterMap().isEmpty() ? "" : "masked";
        }
        String queryString = request.getQueryString();
        String queryClause = StringUtils.hasLength(queryString) ? "?" + queryString : "";
        if (printRequestParameters(request.getMethod())) {
            log.info("{} {}{},request body={{}}", request.getMethod(), getRequestUri(request), queryClause, params);
        } else {
            log.info("{} {}{}", request.getMethod(), getRequestUri(request), queryClause);
        }
        return true;
    }

    private boolean printRequestParameters(String method) {
        return !HttpMethod.GET.name().equalsIgnoreCase(method);
    }

    private static String getRequestUri(HttpServletRequest request) {
        String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        if (uri == null) {
            uri = request.getRequestURL().toString();
        }

        return uri;
    }

}
