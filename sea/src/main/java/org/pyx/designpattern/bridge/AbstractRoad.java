package org.pyx.designpattern.bridge;

/**
 * @author pyx
 * @date 2021/12/28
 */
public abstract class AbstractRoad implements Road{
    protected Car car;

    public AbstractRoad(Car car){
        this.car = car;
    }

    @Override
    public void run(){
        System.out.println("行驶");
    }
}
