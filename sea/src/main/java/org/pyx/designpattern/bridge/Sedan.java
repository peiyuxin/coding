package org.pyx.designpattern.bridge;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class Sedan implements Car {

    @Override
    public void drive() {
        System.out.print("小轿车");
    }
}
