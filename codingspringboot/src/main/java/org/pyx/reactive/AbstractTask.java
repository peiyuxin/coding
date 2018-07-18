package org.pyx.reactive;

/**
 * @see java.util.Observer
 * AbstractTask类相当于Rxjava里面的Observable,Callback类相当于Observer
 * Created by pyx on 2018/7/15.
 * @author pyx
 * @date 2018/7/15
 */
public abstract class AbstractTask<T> implements Task<T>{
    @Override
    public abstract void doHandle(CallBack<T> callback);




    protected <R> Task<R> map(Func<T,Task<R>> func){
        return new Task<R>() {
            @Override
            public void doHandle(CallBack<R> rCallback) {
                AbstractTask.this.doHandle(new CallBack<T>() {
                    @Override
                    public void onResult(T t) {
                        Task<R> rTask = func.call(t);
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
