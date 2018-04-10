package one.show.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java反射工具类
 * <p/>
 * Created by Haliaeetus leucocephalus on 15/3/4.
 */
public class ReflectionUtils {
    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {

        }
    }

    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {

        }
        return result;
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }
    
    /**
     * 获取对象 属性类型(type)，属性名(name)，属性值(value)的map组成的list 
     * @param obj
     * @return
     */
    public static List<Map<String, Object>> getFiledsInfo(Object obj){
    		Field[] fields = obj.getClass().getDeclaredFields();
    		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		Map<String, Object> infoMap = null;
    		for (Field field : fields) {
    			infoMap = new HashMap<String, Object>();
    			infoMap.put("type",field.getType());
    			infoMap.put("name", field.getName());
    			infoMap.put("value", getFieldValue(obj, field.getName()));
    			list.add(infoMap);
		}
    		return list;
    }
    
    /**
     * java实体转map
     * @param obj
     * @return
     */
    public static Map<String, Object> convertObjToMap(Object obj){
    		Field[] fields = obj.getClass().getDeclaredFields();
    		Map<String, Object> infoMap = new HashMap<String, Object>();
    		for (Field field : fields) {
    			infoMap.put(field.getName(), getFieldValue(obj, field.getName()));
		}
    		return infoMap;
    }
}
