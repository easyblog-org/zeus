package top.easyblog.titan.feign.internal;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import top.easyblog.titan.constant.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2021-12-04 12:28
 */
public class FeignLogger extends Logger {

    private final org.slf4j.Logger logger;
    private final boolean needFilter;
    private final Set<String> urlExcludes;

    public FeignLogger(Class<?> clazz, String env) {
        this(LoggerFactory.getLogger(clazz), env, Collections.emptySet());
    }

    public FeignLogger(org.slf4j.Logger logger, String env, Set<String> urlExcludes) {
        this.logger = logger;
        this.needFilter = StringUtils.isNotEmpty(env) && "prod".equalsIgnoreCase(env);
        this.urlExcludes = urlExcludes;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        logger.info(String.format(methodTag(configKey) + format, args));
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        logRequestId();
        String url = request.url();

        if (needSkip(url)) {
            return;
        }
        String headers = getHeaders(request);

        if (isNoBody(request)) {
            logger.info(configKey + "---> {} {} headers: {}", request.httpMethod().name(), url, headers);
        } else {
            int bodyLength = request.body().length;
            String bodyContext = bodyLength == 0 ? "empty body" : new String(request.body(), StandardCharsets.UTF_8);

            logger.info(configKey + "--> {} {} body: {} ({} bytes) headers: {}",
                    request.httpMethod().name(), url, bodyContext, bodyLength, headers);
        }
    }

    private boolean isNoBody(Request request) {
        return request.httpMethod() == Request.HttpMethod.GET ||
                request.httpMethod() == Request.HttpMethod.DELETE ||
                request.body().length == 0;
    }

    private void logRequestId() {
        String requestId = MDC.get(Constants.REQUEST_ID);
        MDC.put(Constants.REQUEST_ID, requestId);
    }

    private boolean needSkip(String url) {
        return needFilter && !StringUtils.isEmpty(url) && urlExcludes.stream().anyMatch(url::contains);
    }

    private String getHeaders(Request request) {
        return request.headers().entrySet().stream()
                .map(entry -> {
                    Collection<String> values = entry.getValue();
                    StringJoiner joiner = new StringJoiner(",");
                    values.forEach(joiner::add);
                    return entry.getKey() + "=" + joiner;
                })
                .collect(Collectors.joining(","));
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        int status = response.status();
        int bodyLength = 0;
        String reason = response.reason();
        String url = response.request().url();

        if (Objects.nonNull(response.body()) && !(status == 204 || status == 205)) {
            byte[] bodyBytes = Util.toByteArray(response.body().asInputStream());
            bodyLength = bodyBytes.length;
            String bodyContext = Util.decodeOrDefault(bodyBytes, StandardCharsets.UTF_8, "empty body");

            if (!needSkip(url)) {
                logger.info(configKey + "<--- {} {} ({}ms) response: {} ({} bytes)", status, reason, elapsedTime, bodyContext, bodyLength);
            }
            return response.toBuilder().body(bodyBytes).build();
        } else {
            if (!needSkip(url)) {
                logger.info(configKey + "<--- {} {} ({}ms) response: empty ({} bytes)", status, reason, elapsedTime, bodyLength);
            }
        }

        return response;
    }
}
