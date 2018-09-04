package org.pyx.natives.lang.inherit;

/**
 * @author pyx
 * @date 2018/9/4
 */
public class B extends A implements InterfaceA {

    public static void main(String[] args) {
        B b = new B();
        b.hello();
    }
}
