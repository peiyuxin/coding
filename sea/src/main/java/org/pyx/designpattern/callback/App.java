package org.pyx.designpattern.callback;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class App {

    public static void main(String[] args) {
        Task task = new SimpleTask();
        Callback cb = new Callback() {
            @Override
            public void call() {
                System.out.println("Call back is work");
            }
        };
        task.executeWith(cb);
    }
}
