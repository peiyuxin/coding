package org.pyx.reactive;

/**
 * Created by pyx on 2018/7/15.
 * @author pyx
 * @date 2018/7/15
 */
public abstract class AbstractTask<T> implements Task<T>{
    @Override
    public abstract void doHandle(CallBack<T> callback);
    protected <R> AbstractTask<R> map(Func<T,AbstractTask<R>> func){
        AbstractTask<T> origin = this;
        return new AbstractTask<R>() {
            @Override
            public void doHandle(CallBack<R> rCallback) {
                origin.doHandle(new CallBack<T>() {
                    @Override
                    public void onResult(T t) {
                        AbstractTask<R> rTask = func.call(t);
                        rTask.doHandle(rCallback);
                    }

                    @Override
                    public void onError(Exception e) {
                        rCallback.onError(e);
                    }
                });
            }
        };
    }
}
