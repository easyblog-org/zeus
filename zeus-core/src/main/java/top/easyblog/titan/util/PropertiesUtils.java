package top.easyblog.titan.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-25 21:05
 */
public class PropertiesUtils {


    private PropertiesUtils() {
    }


    public static Map<String, Object> getFieldValuesMap(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }


    public static boolean allFieldsAreNull(Object obj) throws IllegalAccessException {
        if (Objects.isNull(obj)) return true;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj) != null) {
                return false;
            }
        }
        return true;
    }


}
