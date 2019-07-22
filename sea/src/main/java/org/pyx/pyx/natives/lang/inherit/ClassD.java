package org.pyx.pyx.natives.lang.inherit;

/**
 * @author pyx
 * @date 2018/9/4
 */
public class ClassD implements InterfaceB,InterfaceC{

    public static void main(String[] args) {
        new ClassD().hello();
    }

    @Override
    public void hello() {
        InterfaceB.super.hello();
    }
}
