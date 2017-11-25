package org.kxw.commons.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 集合工具类
 * Create by kangxiongwei on 2017/11/25 上午9:44
 */
@SuppressWarnings("unchecked")
public final class CollectionUtils {

    /**
     * 求两个集合的差集
     * origin和target做减法
     *
     * @param origin
     * @param target
     * @return
     */
    public static List differenceList(Collection origin, Collection target) {
        if (origin == null) return null;
        List list = new ArrayList();
        origin.stream()
                .filter(s -> !target.contains(s))
                .forEach(list::add);
        return list;
    }

    /**
     * 求两个集合的交集
     *
     * @param origin
     * @param target
     * @return
     */
    public static List intersectionList(Collection origin, Collection target) {
        if (origin == null) return null;
        List list = new ArrayList();
        origin.stream().filter(target::contains).forEach(list::add);
        return list;
    }


}
