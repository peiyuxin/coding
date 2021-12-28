package org.pyx.designpattern.bridge.enhancement;

import org.pyx.designpattern.bridge.Road;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class Woman extends AbstractPerson implements Person {
    public Woman(Road road) {
        super(road);
    }

    @Override
    public void work() {
        System.out.print("女人");
        super.work();
        road.run();
    }
}
