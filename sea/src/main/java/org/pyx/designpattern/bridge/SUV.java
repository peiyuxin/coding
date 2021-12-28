package org.pyx.designpattern.bridge;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class SUV implements Car {
    @Override
    public void drive() {
        System.out.print("SUV");
    }
}
