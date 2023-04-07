package org.pyx.designpattern.callback;

/**
 * @author pyx
 * @date 2023/4/7
 */
public class SimpleTask extends Task{
    @Override
    public void execute(){
        System.out.println("Perform some important activity");
    }
}
