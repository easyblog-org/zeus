package top.easyblog.titan.util;

import java.util.UUID;

/**
 * @author: frank.huang
 * @date: 2021-11-01 19:12
 */
public class IdGenerator {

    private IdGenerator(){}

    public static String getRequestId(){
        return getUUID();
    }

    public static String getTraceId(){
        return getUUID() + ((int)((Math.random() * 9 + 1) * 100000));
    }


    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }


}
