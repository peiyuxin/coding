package org.pyx.reactive;

/**
 * 代表函数的接口
 * Created by pyx on 2018/7/15.
 */
public interface Func<T,R>{
    public R call(T param);
}