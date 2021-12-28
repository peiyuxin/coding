package org.pyx.designpattern.bridge.enhancement;

import org.pyx.designpattern.bridge.Road;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class Man extends AbstractPerson implements Person {
    public Man(Road road) {
        super(road);
    }

    @Override
    public void work() {
        System.out.print("男人");
        super.work();
        road.run();
    }
}
