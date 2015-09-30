package util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TypeArgumentUtils {

    public static Class[] getTypeArgument(Class clz) {
        Class[] type = null;
        ParameterizedType parameterizedType = (ParameterizedType) clz.getGenericSuperclass();
        if (parameterizedType != null) {
            Type[] types = parameterizedType.getActualTypeArguments();
            try {
                type = new Class[types.length];
                for (int i = 0; i < types.length; i++) {
                    type[i] = (Class) types[i];
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return type;
    }

    public static Class[] getTypeArgument2(Class clz) {

        return null;
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        System.out.println(ReflectionToStringBuilder.toString(list.getClass().getTypeParameters()));
        // TypeVariable[] type = list.getClass().
        // System.out.println(type[0]);

        System.out.println(ReflectionToStringBuilder.toString(TypeArgumentUtils.getTypeArgument(InnerBean2.class)));
        System.out.println(ReflectionToStringBuilder.toString(TypeArgumentUtils.getTypeArgument(InnerBean3.class)));
    }

    public static class InnerBean2 extends ArrayList<Exception> {

    }

    public static class InnerBean3 extends HashMap<String, Exception> {

    }
}
