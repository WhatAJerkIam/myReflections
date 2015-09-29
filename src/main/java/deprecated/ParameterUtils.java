package deprecated;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.reflections.ReflectionUtils;

import util.TypeUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ParameterUtils {

    /**
     * @description: TODO
     * @param clazz
     *            类名
     * @param name
     *            方法名
     * @param argcount
     *            方法可能含有的参数最小值
     * @return
     */
    public static Method getMethod(Class clazz, String name, Integer argcount) {
        if (clazz == null)
            return null;

        Method[] ms = clazz.getMethods();
        for (Method m : ms) {
            if (m.getName().equals(name)) {
                if (argcount == null || argcount <= m.getParameterTypes().length) {
                    return m;
                }
            }
        }
        return null;
    }

    public static Object getParameters(Class clazz, Object source) {
        Object result = null;
        if (clazz == null) {
            result = getParameters(source);
        } else if (TypeUtils.isMap(clazz)) {
            result = parseMap(source);
        } else if (TypeUtils.isList(clazz)) {
            result = parseList(source);
        } else if (TypeUtils.isArray(clazz)) {
            result = parseArray(source);
        } else if (TypeUtils.isString(clazz) || TypeUtils.isEnum(clazz) || TypeUtils.isBoolean(clazz)
                || TypeUtils.isNumber(clazz) || TypeUtils.isDate(clazz)) {
            result = String.valueOf(source);
            // } else if (isBoolean(clazz)) {
            // result = parseBoolean(source);
            // } else if (isNumber(clazz)) {
            // result = parseNumber(clazz, source);
        } else {
            result = parseBean(clazz, source);
        }

        return result;
    }

    private static Object parseBean(Class clazz, Object source) {
        Set<Field> fields = ReflectionUtils.getAllFields(clazz);
        Map paramMap = toMap(source);
        Map map = new HashMap();
        for (Field f : fields) {
            System.out.println(f.getName());
            if (paramMap.containsKey(f.getName())) {
                // System.out.println("name=" + f.getName());
                // System.out.println("declare=" + f.getDeclaringClass());
                // System.out.println("declare2=" + f.getType().getName());
                Object param = getParameters(f.getType(), paramMap.get(f.getName()));
                if (param != null) {
                    map.put(f.getName(), param);
                }
                System.out.println(param);
            }
        }
        map.put("class", clazz.getName());
        return map;
    }

    private static Map<String, Object> toMap(Object source) {
        if (TypeUtils.isMap(source))
            return (Map) source;

        Map map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(source);
                    map.put(key, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }
        return map;
    }

    private static Object parseArray(Object source) {
        return null;
    }

    private static Object parseList(Object source) {
        if (TypeUtils.isList(source))
            return (List) source;
        // throw new BadRequestException("参数格式异常：" + ReflectionToStringBuilder.toString(source)
        // + "can not be cast to a list.");
        return null;
    }

    private static Object parseMap(Object source) {
        if (TypeUtils.isMap(source))
            return (Map) source;
        // throw new BadRequestException("参数格式异常：" + ReflectionToStringBuilder.toString(source)
        // + "can not be cast to a map.");
        return null;
    }

    private static Object getParameters(Object source) {
        return JSONObject.fromObject(source);
    }

    public static void main(String[] args) {
        System.out.println(Map.class.isAssignableFrom(HashMap.class));
        System.out.println(Map.class.isAssignableFrom(HashSet.class));

        // ReflectionUtils.getConstructors(t, predicates);
    }
}
