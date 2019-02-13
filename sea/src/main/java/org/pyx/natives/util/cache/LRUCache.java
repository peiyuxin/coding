package org.pyx.natives.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author pyx
 * @date 2019/1/4
 */
public class LRUCache<K, V> extends LinkedHashMap<K,V> {
    private int cacheSize;

    public LRUCache(int cacheSize){
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}
