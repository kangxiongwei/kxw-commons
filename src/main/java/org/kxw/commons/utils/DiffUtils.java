package org.kxw.commons.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 比较两个对象的不同
 * Create by kangxiongwei on 2017/11/25 上午9:39
 */
public final class DiffUtils {


    /**
     * 比较两个对象的不同
     * 比较结果是一个对象，key为field名称，value为DiffModel对象
     *
     * @param origin
     * @param target
     * @return
     */
    public static String diff(Object origin, Object target) throws Exception {
        if (origin == null && target == null) return null;
        if (origin == null) return JSON.toJSONString(new DiffModel(target, DiffType.INSERT, target));
        if (target == null) return JSON.toJSONString(new DiffModel(origin, DiffType.DELETE, null));
        Map<String, Object> map1 = ReflectUtils.bean2ShallowMap(origin);
        Map<String, Object> map2 = ReflectUtils.bean2ShallowMap(target);
        //比较map1和map2的差
        Map<String, DiffModel> left = diff(map1, map2);
        //比较map2和map1的差
        Map<String, DiffModel> right = diff(map2, map1);
        //所有的不同
        Map<String, DiffModel> all = new HashMap<>();
        all.putAll(left);
        all.putAll(right);
        return JSON.toJSONString(all);
    }

    /**
     * 比较两个map的差集
     *
     * @param map1
     * @param map2
     * @return
     */
    private static Map<String, DiffModel> diff(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, DiffModel> diffModelMap = new HashMap<>();
        for (String key : map1.keySet()) {
            Object value1 = map1.get(key);
            if (map2.containsKey(key)) {
                //两边都有，比较值是否相等
                Object value2 = map2.get(key);
                if (value1.equals(value2)) continue;
                diffModelMap.put(key, new DiffModel(value1, DiffType.CHANGED, value2));
            } else {
                //左边有，右边没有
                diffModelMap.put(key, new DiffModel(value1, DiffType.DELETE, null));
            }
        }
        return diffModelMap;
    }


    /**
     * 不同处
     */
    static class DiffModel {
        private Object left; //原来的数据
        private DiffType diffType; //不同的类型
        private Object right; //新的数据

        public DiffModel(Object left, DiffType diffType, Object right) {
            this.left = left;
            this.diffType = diffType;
            this.right = right;
        }

        public Object getLeft() {
            return left;
        }

        public void setLeft(Object left) {
            this.left = left;
        }

        public DiffType getDiffType() {
            return diffType;
        }

        public void setDiffType(DiffType diffType) {
            this.diffType = diffType;
        }

        public Object getRight() {
            return right;
        }

        public void setRight(Object right) {
            this.right = right;
        }
    }

    enum DiffType {
        DELETE, INSERT, CHANGED;
    }

}
