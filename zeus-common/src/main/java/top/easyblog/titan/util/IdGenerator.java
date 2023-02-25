package top.easyblog.titan.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;

/**
 * @author: frank.huang
 * @date: 2021-11-01 19:12
 */
public class IdGenerator {

    private final static String NUM_STR = "0123456789";

    private IdGenerator() {
    }

    /**
     * 生成请求id
     *
     * @return
     */
    public static String getRequestId() {
        return getUUID();
    }


    public static String getTraceId() {
        return getUUID() + ((int) ((Math.random() * 9 + 1) * 100000));
    }


    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getUUID(int len) {
        return UUID.randomUUID().toString().replace("-", "").substring(len);
    }


    public static String generateCaptchaCode(int len) {
        if (len <= 0) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<len;i++){
            sb.append(new Random().nextInt(NUM_STR.length()));
        }
        return sb.toString();
    }

}
