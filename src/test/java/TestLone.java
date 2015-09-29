

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TestLone {

    public static class InnerBean {
        private Map<String, String> map;
        private List<Exception> list;
        private List<? extends Exception> list2;
        private List<?> list4;
        // private List list3;

    }

    public static class InnerBean2 extends ArrayList<Exception> {

    }

    public static void main(String[] args) {
        InnerBean bean = new InnerBean();
        print(bean);

        Field[] fs = bean.getClass().getDeclaredFields();
        print(fs);
        for (Field f : fs) {
            print(((ParameterizedType) f.getGenericType()).getActualTypeArguments());
        }

        print(InnerBean2.class.getGenericInterfaces());
        System.out.println(((ParameterizedType) InnerBean2.class.getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public static void print(Object obj) {
        System.out.println(ReflectionToStringBuilder.toString(obj));
    }
}
