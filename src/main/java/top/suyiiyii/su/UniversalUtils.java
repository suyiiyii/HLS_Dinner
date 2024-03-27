package top.suyiiyii.su;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 通用工具类
 *
 * @author suyiiyii
 */
public class UniversalUtils {
    /**
     * 把对象转换成json字符串
     *
     * @param object Object
     * @return String
     * @throws IOException IOException
     */
    public static String obj2Json(Object object) throws IOException {
        return WebUtils.MAPPER.writeValueAsString(object);
    }

    /**
     * 把json字符串转换成对象
     *
     * @param json      String
     * @param valueType Class<T>
     * @param <T>       T
     * @return T
     * @throws IOException IOException
     */

    public static <T> T json2Obj(String json, Class<T> valueType) throws IOException {
        return WebUtils.MAPPER.readValue(json, valueType);
    }

    /**
     * 下划线转小驼峰
     *
     * @param downLineStr String
     * @return String
     */
    public static String downToCaml(String downLineStr) {
        StringBuilder builder = new StringBuilder();
        boolean isAfterDownLine = false;
        for (int i = 0; i < downLineStr.length(); i++) {
            char c = downLineStr.charAt(i);
            if (c == '_') {
                isAfterDownLine = true;
                continue;
            }
            if (isAfterDownLine) {
                c = Character.toUpperCase(c);
                isAfterDownLine = false;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * 小驼峰转下划线
     *
     * @param camlStr String
     * @return String
     */
    public static String camlToDown(String camlStr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < camlStr.length(); i++) {
            char c = camlStr.charAt(i);
            if (Character.isUpperCase(c)) {
                builder.append("_");
                builder.append(Character.toLowerCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 浅拷贝一个对象
     *
     * @param obj T
     * @param <T> T
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T obj) {
        try {
            T cloneObj = (T) obj.getClass().newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                field.set(cloneObj, value);
            }
            return cloneObj;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断两个对象是否相等
     * 通过反射获取所有字段，逐一比较
     * 仅支持基本数据类型和String
     *
     * @param x Object
     * @param y Object
     * @return boolean
     */
    public static boolean equal(Object x, Object y) {
        if (x == y) {
            return true;
        }
        if (x.getClass() != y.getClass()) {
            return false;
        }
        try {
            Field[] fields = x.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object obj1 = field.get(x);
                Object obj2 = field.get(y);
                if (!obj1.equals(obj2)) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
