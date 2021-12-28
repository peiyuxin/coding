package org.pyx.designpattern.bridge;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class Highway extends AbstractRoad implements Road {
    public Highway(Car car) {
        super(car);
    }
    @Override
    public void run(){
        car.drive();
        System.out.print("在高速公路");
        super.run();
    }
}
