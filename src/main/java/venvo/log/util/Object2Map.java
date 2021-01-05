package venvo.log.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author venvo
 * @date 2020-12-30 12:54
 * @description Object转Map
 * @modified By
 * @version: jdk1.8
 */
public class Object2Map {

    private Object2Map(){}

    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null){
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }

}
