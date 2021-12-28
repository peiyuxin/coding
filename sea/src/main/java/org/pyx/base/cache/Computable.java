package org.pyx.base.cache;

import java.util.concurrent.Callable;

/**
 * @author pyx
 * @date 2021/12/28
 */
public interface Computable<K, V>  {

    /**
     * 通过关键字来计算
     * @param key 查找关键字
     * @param callable #@see Callable
     *
     * @return 计算结果
     */
    V get(K key, Callable<V> callable);
}
