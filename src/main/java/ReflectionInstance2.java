

import java.lang.reflect.Field;
import java.util.Set;

import org.reflections.ReflectionUtils;

import com.thoughtworks.xstream.InitializationException;

public class ReflectionInstance2 {

    private Class beanClass;
    private Object values;

    public ReflectionInstance2(Class clazz, Object obj) {
        if (clazz == null)
            throw new InitializationException("class is null");
        this.beanClass = clazz;
        this.values = obj;
    }

    public Object build() {
        Class clz = this.beanClass;
        Object instance = null;
        Set<Field> fields = ReflectionUtils.getAllFields(clz, new NoStaticFieldPredicate());

        return instance;
    }
}
