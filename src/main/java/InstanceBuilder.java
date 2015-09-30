import java.lang.reflect.Constructor;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.reflections.ReflectionUtils;

import util.TypeUtils;

import com.google.common.primitives.Primitives;
import com.wangyin.industry.fundshop.query.enums.FundQueryOrderColumn;
import com.wangyin.industry.fundshop.query.model.FundInfoQueryNSortRequest;

public class InstanceBuilder {

    private Class beanClass;
    private Object values;

    public InstanceBuilder(Class clazz, Object obj) {
        if (clazz == null)
            throw new IllegalArgumentException("class is null");
        this.beanClass = clazz;
        this.values = obj;
    }

    public Object build() {
        Class clz = this.beanClass;
        Object instance = null;
        if (isNull(values))
            return null;

        if (String.class.equals(clz)) {
            instance = String.valueOf(values);
        } else if (TypeUtils.isEnum(clz)) {
            instance = getEnum(clz, values);
        } else if (clz.isPrimitive() || TypeUtils.isWrapClass(clz)) {
            instance = getPrimitive(clz, values);
        } else if (TypeUtils.isMap(clz)) {
            instance = parseMap(clz, values);
        } else if (TypeUtils.isList(clz)) {
            instance = parseList(clz, values);
        } else if (clz.isInterface() || Class.class.equals(clz)) {
            // do nothing
        } else if (TypeUtils.isNumber(clz)) {
            instance = parseNumber(clz, values);
        } else if (TypeUtils.isDate(clz)) {
            instance = parseDate(clz, values);
        } else {
            instance = parseBean(clz, values);
        }
        // Set<Field> fields = ReflectionUtils.getAllFields(clz, new NoStaticFieldPredicate());
        return instance;
    }

    private Object parseBean(Class clz, Object values2) {
        // TODO Auto-generated method stub
        return null;
    }

    private Object parseDate(Class clz, Object values2) {
        return null;
    }

    private Object parseNumber(Class clz, Object values2) {
        // TODO Auto-generated method stub
        return null;
    }

    private Object parseList(Class clz, Object values2) {
        // TODO Auto-generated method stub
        return null;
    }

    private Object parseMap(Class clz, Object values2) {
        // TODO Auto-generated method stub
        return null;
    }

    private Object getPrimitive(Class clz, Object values2) {
        if (isNull(values2))
            return null;
        clz = Primitives.wrap(clz);
        if (clz.equals(Primitives.wrap(values2.getClass()))) {
            return values2;
        } else if (TypeUtils.isBoolean(clz)) {
            Boolean bool = BooleanUtils.toBooleanObject(values2.toString());
            if (bool == null)
                throw new IllegalArgumentException(values2 + " can not be cast to " + clz.getName());
            return bool;

        } else if (clz.equals(Character.class)) {
            if (values2 instanceof String && ((String) values2).length() == 1) {
                return ((String) values2).charAt(0);
            }
            throw new IllegalArgumentException(values2 + " can not be cast to " + clz.getName());
        } else {
            Set<Constructor> constructors = ReflectionUtils.getConstructors(clz,
                ReflectionUtils.withParameters(String.class));
            if (!constructors.isEmpty()) {
                Constructor constructor = (Constructor) constructors.toArray()[0];
                try {
                    Object prim = constructor.newInstance(values2);
                    return prim;
                } catch (Exception e) {
                    throw new IllegalArgumentException("primitive type cannot be created.");
                }
            } else {

                return null;
            }
        }

    }

    private Object getEnum(Class clz, Object values2) {
        if (isNull(values2))
            return null;
        Object[] enumms = clz.getEnumConstants();
        for (Object enumm : enumms) {
            if (enumm.equals(values2) || enumm.toString().equals(values2)) {
                return enumm;
            }
        }
        throw new ClassCastException(values2 + " can not be cast to " + clz.getName());
    }

    public static boolean isNull(Object obj) {
        return obj == null || "null".equalsIgnoreCase(String.valueOf(obj));

    }

    public static void main(String[] args) {
        InstanceBuilder con = new InstanceBuilder(FundInfoQueryNSortRequest.class, null);
        Object enumm = con.getEnum(FundQueryOrderColumn.class, FundQueryOrderColumn.RISKLEVEL);
        System.out.println(enumm.getClass());

        Object prim = con.getPrimitive(Integer.class, Integer.MAX_VALUE);
        System.out.println(prim);
        Object prim2 = con.getPrimitive(Integer.class, String.valueOf(Integer.MAX_VALUE));
        System.out.println(prim2);

        Object prim3 = con.getPrimitive(Long.class, Long.MAX_VALUE);
        System.out.println(prim3);
        Object prim4 = con.getPrimitive(Long.class, String.valueOf(Long.MAX_VALUE));
        System.out.println(prim4);

        Object prim5 = con.getPrimitive(Double.class, Double.MAX_VALUE);
        System.out.println(prim5);
        Object prim6 = con.getPrimitive(Double.class, String.valueOf(Double.MAX_VALUE));
        System.out.println(prim6);

        Object prim51 = con.getPrimitive(Float.class, Float.MAX_VALUE);
        System.out.println(prim51);
        Object prim61 = con.getPrimitive(Float.class, String.valueOf(Float.MAX_VALUE));
        System.out.println(prim61);

        Object prim7 = con.getPrimitive(Byte.class, Byte.MAX_VALUE);
        System.out.println(prim7);
        Object prim8 = con.getPrimitive(Byte.class, String.valueOf(Byte.MAX_VALUE));
        System.out.println(prim8);

        Object prim52 = con.getPrimitive(Character.class, 'c');
        System.out.println(prim52);
        Object prim62 = con.getPrimitive(Character.class, "C");
        System.out.println(prim62);

        Object prim521 = con.getPrimitive(Boolean.class, "true");
        System.out.println(prim521);
        Object prim621 = con.getPrimitive(Boolean.class, "FaLsE");
        System.out.println(prim621);
    }

}
