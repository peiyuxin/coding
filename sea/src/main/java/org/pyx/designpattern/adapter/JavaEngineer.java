package org.pyx.designpattern.adapter;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class JavaEngineer implements Engineer{

    @Override
    public void operateDevice() {
        System.out.println("do something...");
    }
}
