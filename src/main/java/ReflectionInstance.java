

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.ReflectionUtils;

import com.google.common.primitives.Primitives;
import com.thoughtworks.xstream.InitializationException;
import com.wangyin.industry.fundshop.query.model.FundInfoQueryNSortRequest;

public class ReflectionInstance {

    public Map<Class, Object> defaultMap;
    @Getter
    public Object instance;
    @Getter
    public Class beanClass;

    private static final Log log = LogFactory.getLog(ReflectionInstance.class);

    public static final Map<Class, Object> STATIC_MAP;
    static {
        Map<Class, Object> map = new HashMap();
        map.put(Integer.class, 0);
        map.put(Short.class, 0);
        map.put(Byte.class, 0);
        map.put(Character.class, '\n');
        map.put(Double.class, Math.PI);
        map.put(Float.class, Double.valueOf(Math.PI).floatValue());
        map.put(Boolean.class, Boolean.FALSE);
        map.put(Long.class, 0l);
        map.put(String.class, "0");
        map.put(BigDecimal.class, BigDecimal.ZERO);
        STATIC_MAP = MapUtils.unmodifiableMap(map);
    }

    public ReflectionInstance(Class clz) {
        this(clz, STATIC_MAP);
    }

    public ReflectionInstance(Class clz, Map<Class, Object> defaultMap) {
        if (clz == null)
            throw new InitializationException("class is null");
        this.beanClass = clz;
        this.defaultMap = defaultMap == null ? STATIC_MAP : defaultMap;
    }

    public Object build() {
        Class clz = this.beanClass;
        Object instance = null;

        try {
            if (String.class.equals(clz)) {
                instance = MapUtils.getObject(this.defaultMap, clz);
            } else if (TypeUtils.isEnum(clz)) {
                instance = getEnum(clz);
            } else if (clz.isPrimitive() || TypeUtils.isWrapClass(clz)) {
                clz = Primitives.wrap(clz);
                instance = MapUtils.getObject(this.defaultMap, clz);
            } else if (TypeUtils.isMap(clz)) {
                instance = parseMap(clz);
            } else if (TypeUtils.isList(clz)) {
                instance = parseList(clz);
            } else if (clz.isInterface() || Class.class.equals(clz)) {
                // do nothing
            } else if (TypeUtils.isNumber(clz)) {
                instance = MapUtils.getObject(this.defaultMap, clz);
            } else if (TypeUtils.isDate(clz)) {
                instance = new Date();
            } else {
                instance = clz.newInstance();

                Set<Field> fields = ReflectionUtils.getAllFields(clz, new NoStaticFieldPredicate());

                if (fields != null) {
                    for (Field p : fields) {
                        Class subClass = p.getType();
                        ReflectionInstance subInstanceBuilder = new ReflectionInstance(subClass, this.defaultMap);
                        Object ins = subInstanceBuilder.build();
                        setFieldValue(instance, ins, p);
                    }
                }

            }

        } catch (Exception e) {
            log.error("类解析异常: class= " + clz.getName(), e);
            // throw new InternalErrorException("类解析异常", e);
        }

        return instance;
    }

    private List parseList(Class<? extends List> clz) {
        List list = new ArrayList();
        try {
            Type[] types = ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments();
            if (types.length == 1) {
                Class genetype = (Class) ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments()[0];
                ReflectionInstance subInstanceBuilder = new ReflectionInstance(genetype, this.defaultMap);
                Object ins = subInstanceBuilder.build();
                list.add(ins);
            }
        } catch (Exception cce) {
        }
        return list;
    }

    private Map parseMap(Class<? extends Map> clz) {
        Map map = new HashMap();
        return map;
    }

    private Object getEnum(Class subClass) {
        return subClass.getEnumConstants()[0];
    }

    private void setFieldValue(Object entityToBuild, final Object parameter, final Field fieldToSet) {
        if (!fieldToSet.isAccessible()) {
            fieldToSet.setAccessible(true);
        }
        try {
            fieldToSet.set(entityToBuild, parameter);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // ReflectionInstance r1 = new ReflectionInstance(TransferOutOrder.class);
        ReflectionInstance r2 = new ReflectionInstance(FundInfoQueryNSortRequest.class);

        // System.out.println(ReflectionToStringBuilder.toString(r1.build()));
        System.out.println(ReflectionToStringBuilder.toString(r2.build()));

    }

}
