package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/14.
 */
public interface CallBack<T> {
    public void onResult(T t);
    public void onError(Exception e);
}
