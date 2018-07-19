package org.pyx.natives.generic;

/**
 * @author pyx
 * @date 2018/7/19
 */
public class Box<T> {
    private T data;


    public Box(){}
    public Box(T data){
        this.data =  data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
