package org.pyx.designpattern.adapter;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class App {

    public static void main(String[] args) {
        Engineer engineer = new JavaEngineeringManager();
        engineer.operateDevice();
    }
}
