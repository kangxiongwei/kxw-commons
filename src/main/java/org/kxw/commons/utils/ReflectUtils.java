package org.kxw.commons.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 反射工具类
 * Create by kangxiongwei on 2017/11/25 上午12:13
 */
@SuppressWarnings("unchecked")
public final class ReflectUtils {

    /**
     * 根据属性名称，获取属性值
     *
     * @param fieldName 属性名称
     * @param bean      对象
     * @return
     */
    public static Object getFieldValue(String fieldName, Object bean) throws Exception {
        Objects.requireNonNull(bean);
        Objects.requireNonNull(fieldName);
        Field field = bean.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(bean);
    }

    /**
     * 把一个对象转为map，其中key为属性名称，value为属性值
     * 只会把每个属性映射为map，如果属性为Object，不会进行深层次映射
     *
     * @param bean 需要转换的bean
     * @return
     */
    public static Map<String, Object> bean2ShallowMap(Object bean) throws Exception {
        Objects.requireNonNull(bean);
        Map<String, Object> map = new HashMap<>();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(bean));
        }
        return map;
    }

    /**
     * 把一个对象转为map，其中key为属性名称，value为属性值
     * 会把非基本数据类型的对象，全部映射为map，一层一层嵌套
     * 目前不支持属性为Map<Object, Object>的类型，支持Map的key为基本数据类型或枚举
     *
     * @param bean 需要转换的bean
     * @return
     */
    public static Map<String, Object> bean2DeepMap(Object bean) throws Exception {
        if (bean == null) return null;
        Map<String, Object> beanMap = new HashMap<>();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class fieldType = field.getType();
            if (isPrimitive(fieldType)) {
                //基本数据类型，直接放入map
                beanMap.put(field.getName(), field.get(bean));
            } else if (List.class.isAssignableFrom(fieldType)) {
                List beanList = (List) field.get(bean);
                if (beanList == null || beanList.isEmpty()) continue;
                beanMap.put(field.getName(), isPrimitive(beanList) ? beanList : listObject2Map(fieldType, beanList));
            } else if (Set.class.isAssignableFrom(fieldType)) {
                Set beanSet = (Set) field.get(bean);
                if (beanSet == null || beanSet.isEmpty()) continue;
                beanMap.put(field.getName(), isPrimitive(beanSet) ? beanSet : setObject2Map(fieldType, beanSet));
            } else if (Map.class.isAssignableFrom(fieldType)) {
                Map map = (Map) field.get(bean);
                if (map == null || map.isEmpty()) continue;
                beanMap.put(field.getName(), isPrimitive(map) ? map : mapObject2Map(fieldType, map));
            } else {
                //普通的自定义对象
                beanMap.put(field.getName(), bean2DeepMap(field.get(bean)));
            }
        }
        return beanMap;
    }

    private static Map<String, Map<String, Object>> mapObject2Map(Class fieldType, Map map) throws Exception {
        Map<String, Map<String, Object>> maps = (Map<String, Map<String, Object>>) fieldType.newInstance();
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            maps.put(String.valueOf(key), bean2DeepMap(value));
        }
        return maps;
    }

    private static Set<Map<String, Object>> setObject2Map(Class fieldType, Set beanSet) throws Exception {
        Set<Map<String, Object>> sets = (Set<Map<String, Object>>) fieldType.newInstance();
        for (Object obj : beanSet) {
            sets.add(bean2DeepMap(obj));
        }
        return sets;
    }

    private static List<Map<String, Object>> listObject2Map(Class fieldType, List beanList) throws Exception {
        List<Map<String, Object>> maps = (List<Map<String, Object>>) fieldType.newInstance();
        for (Object obj : beanList) {
            maps.add(bean2DeepMap(obj));
        }
        return maps;
    }

    /**
     * 判断是否是无需递归的类型
     *
     * @param type
     * @return
     */
    public static boolean isPrimitive(Class type) {
        return type.isPrimitive() || type == String.class || type.isEnum();
    }

    /**
     * 判断某个集合是否是基本数据类型
     *
     * @param collection
     * @return
     */
    public static boolean isPrimitive(Collection collection) {
        Objects.requireNonNull(collection);
        Iterator iterator = collection.iterator();
        Object obj = iterator.next();
        return isPrimitive(obj.getClass());
    }

    /**
     * 判断某个map的value是否是基本数据类型
     *
     * @param map
     * @return
     */
    public static boolean isPrimitive(Map map) {
        Objects.requireNonNull(map);
        Set<Map.Entry> entries = map.entrySet();
        Object obj = entries.iterator().next().getValue();
        return isPrimitive(obj.getClass());
    }


}
