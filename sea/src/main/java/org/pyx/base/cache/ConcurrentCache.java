package org.pyx.base.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 此类不用额外使用自旋锁或者同步器，可保证并发时线程安全，在并发情况下具有高性能
 * @author pyx
 * @date 2021/12/28
 */
public class ConcurrentCache<K,V> implements Computable<K,V> {

    protected final ConcurrentMap<K, Future<V>> concurrentMap;

    public ConcurrentCache() {
        concurrentMap = new ConcurrentHashMap<>();
    }

    public ConcurrentCache(Map<K, V> map) {
        concurrentMap = new ConcurrentHashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            final K key = entry.getKey();
            final V value = entry.getValue();
            concurrentMap.put(key, new FutureTask<V>(new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return value;
                }
            }));
        }
    }

    public static <K, V> Computable<K, V> createComputable() {
        return new ConcurrentCache<K, V>();
    }



    @Override
    public V get(K key, Callable<V> callable) {
        Future<V> future = concurrentMap.get(key);
        if (future == null) {
            FutureTask<V> futureTask = new FutureTask<V>(callable);
            future = concurrentMap.putIfAbsent(key, futureTask);
            if(future == null){
                future = futureTask;
                futureTask.run();
            }
        }

        try{
            return future.get();
        } catch (Exception e){
            concurrentMap.remove(key);
        }
        return null;
    }
}
