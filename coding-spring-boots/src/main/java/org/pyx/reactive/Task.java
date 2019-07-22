package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/15.
 */
public interface Task<T> {
    public void doHandle(CallBack<T> callback);
}
