

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class TypeUtils {

    public static boolean isDate(Class clazz) {
        return clazz != null && (Date.class.isAssignableFrom(clazz));
    }

    public static boolean isArray(Class clazz) {
        return clazz != null
                && (clazz.isArray() || Collection.class.isAssignableFrom(clazz) || (JSONArray.class.isAssignableFrom(clazz)));
    }

    public static boolean isEnum(Class clazz) {
        return clazz != null && (Enum.class.isAssignableFrom(clazz));
    }

    public static boolean isMap(Class clazz) {
        return clazz != null && (Map.class.isAssignableFrom(clazz));
    }

    public static boolean isMap(Object map) {
        return map != null && isMap(map.getClass());
    }

    public static boolean isList(Class clazz) {
        return clazz != null && (List.class.isAssignableFrom(clazz));
    }

    public static boolean isList(Object object) {
        return object != null && isList(object.getClass());
    }

    public static boolean isBoolean(Class clazz) {
        return clazz != null && (Boolean.TYPE.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz));
    }

    public static boolean isDouble(Class clazz) {
        return clazz != null && (Double.TYPE.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz));
    }

    public static boolean isNumber(Class clazz) {
        return clazz != null
                && (Byte.TYPE.isAssignableFrom(clazz) || Short.TYPE.isAssignableFrom(clazz)
                        || Integer.TYPE.isAssignableFrom(clazz) || Long.TYPE.isAssignableFrom(clazz)
                        || Float.TYPE.isAssignableFrom(clazz) || Double.TYPE.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz));
    }

    public static boolean isString(Class clazz) {
        return clazz != null
                && (String.class.isAssignableFrom(clazz) || (Character.TYPE.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)));
    }

    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

}