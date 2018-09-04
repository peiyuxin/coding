package org.pyx.natives.lang.inherit;

/**
 * @author pyx
 * @date 2018/9/4
 */
public interface InterfaceD extends InterfaceB,InterfaceC{
    @Override
    default void hello() {
        System.out.println("hello from D");
    }
}
