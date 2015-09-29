

import java.util.Map;

import net.sf.json.JSONObject;

public class ParamsValidator {

    public static JSONObject toJson(String str) {
        return JSONObject.fromObject(str);
    }

    public static Object[] toArray(Map params, int arraysize) {
        if (arraysize == 1)
            return new Object[] { params };

        Object[] objs = new Object[arraysize];
        for (int i = 0; i < arraysize; i++) {
            if (params.containsKey(i)) {
                objs[i] = params.get(i);
            }
        }
        return objs;

    }

}
