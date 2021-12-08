package org.pyx.apache.commons.collections;

import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.collections.map.LazyMap;
import org.junit.Test;

/**
 * 完全没有范型安全检查，不建议使用
 * @author pyx
 * @date 2021/12/2
 */
public class LazyMapTest {

    @Deprecated
    @Test
    public void testLRUMap() {
        Map<Integer, String> hashMap = Maps.newHashMap();
        hashMap.put(1, "abc");
        hashMap.put(2, "uuu");
        Map<Integer, String> lazyMap = LazyMap.decorate(hashMap, s -> ((String) s).toUpperCase());
        System.out.println(lazyMap);
        System.out.println(lazyMap.get("xx"));
        System.out.println(lazyMap.get(2));
        System.out.println(lazyMap);
    }
}
