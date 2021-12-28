package org.pyx.designpattern.adapter;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class JavaEngineeringManager implements Engineer {
    private Engineer engineer;

    public JavaEngineeringManager(){
        engineer = new JavaEngineer();
    }
    @Override
    public void operateDevice() {
        engineer.operateDevice();
    }
}
