package top.easyblog.titan.util;

import feign.RequestTemplate;

/**
 * @author: frank.huang
 * @date: 2022-03-01 20:13
 */
public class InterceptorUtils {

    public static void query(RequestTemplate template, String key, String value) {
        template.query(key, value);
    }

    public static String now() {
        return String.valueOf(System.currentTimeMillis());
    }
    
}
