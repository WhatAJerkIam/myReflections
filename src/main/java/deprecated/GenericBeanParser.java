package deprecated;


/**
 * @author yangjun3
 * @date 2015年9月15日 下午5:59:49
 * @description: dubbo genericservice接口参数转换
 */
public class GenericBeanParser {

    public Object parse(Class clazz, Object source) throws Exception {
        Object target = null;
        if (String.class.equals(clazz)) {
            target = String.valueOf(source);
        } else if (Integer.class.equals(clazz)) {
            target = Integer.parseInt(String.valueOf(source));
        } else if (Long.class.equals(clazz)) {

        }

        return target;
    }

}
