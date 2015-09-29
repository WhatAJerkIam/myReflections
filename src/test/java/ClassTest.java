import java.io.Serializable;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.reflections.Reflections;

public class ClassTest {

    public static void main(String[] args) {
        Reflections rf = new Reflections("com.wangyin.industry.fundshop.*");
        Set<Class<? extends Serializable>> set = rf.getSubTypesOf(Serializable.class);
        for (Class clz : set) {
            if (!clz.isInterface() && !TypeUtils.isEnum(clz)) {

                try {
                    ReflectionInstance obj = new ReflectionInstance(clz);
                    // ClassTest.print(obj.build());
                    System.out.println(clz.getName() + "|:|" + JSONObject.fromObject(obj.build()));
                } catch (Exception e) {
                    System.out.println(clz.getName());
                    e.printStackTrace();
                }

            }
        }

    }

    public static void print(Object obj) {
        System.out.println(ReflectionToStringBuilder.toString(obj));
    }

}
