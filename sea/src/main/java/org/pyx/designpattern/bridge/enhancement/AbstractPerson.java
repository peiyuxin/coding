package org.pyx.designpattern.bridge.enhancement;

import org.pyx.designpattern.bridge.Road;

/**
 * @author pyx
 * @date 2021/12/28
 */
public abstract class AbstractPerson {
    protected Road road;

    public AbstractPerson(Road road) {
        this.road = road;
    }

    public void work(){
        System.out.print("开着");
    }
}
