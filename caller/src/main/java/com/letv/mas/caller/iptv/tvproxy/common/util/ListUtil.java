package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtil {

    public interface ListHook<T> {
        public boolean test(T t);
    }

    /**
     * 基于外部hook的list过滤
     * @param list
     * @param hook
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(List<T> list, ListHook<T> hook) {
        ArrayList<T> r = new ArrayList<T>();
        for (T t : list) {
            if (hook.test(t)) {
                r.add(t);
            }
        }
        r.trimToSize();
        return r;
    }

    public static <T> Map<String, T> list2Map(List<T> list, String listPropKey) {
        Map<String, T> map = new HashMap<String, T>();
        Class clazz = list.get(0).getClass();
        try {
            Field field = clazz.getDeclaredField(listPropKey);
            field.setAccessible(true);
            for (T t : list) {
                map.put(String.valueOf(field.get(t)), t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
