package org.pyx.designpattern.callback;

/**
 * @author pyx
 * @date 2023/4/7
 */
public abstract class Task {

    public final void executeWith(Callback callback) {
        execute();
        if(callback != null){
            callback.call();
        }
    }
    public abstract void execute();
}
