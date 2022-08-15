package org.pyx.designpattern.bridge;

import org.pyx.designpattern.bridge.enhancement.Man;
import org.pyx.designpattern.bridge.enhancement.Person;
import org.pyx.designpattern.bridge.enhancement.Woman;

/**
 * 类图：http://img.blog.csdn.net/20140330075443203?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvamFzb24wNTM5/font
 * /5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast
 * <p/>
 * <p/>
 * 概述:
 * 在软件系统中，某些类型由于自身的逻辑，它具有两个或多个维度的变化，那么如何应对这种“多维度的变化”？如何利用面向对象的技术来使得该类型能够轻松的沿着多个方向进行变化，而又不引入额外的复杂度？这就要使用Bridge模式。
 * 意图:
 * 　　　将抽象部分与实现部分分离，使它们都可以独立的变化。
 * ——《设计模式》GOF
 * <p/>
 * 桥接模式是一种结构型模式，它主要应对的是：由于实际的需要，某个类具有两个或两个以上的维度变化，如果只是用继承将无法实现这种需要，或者使得设计变得相当臃肿。
 * <p/>
 * 桥接模式的做法是把变化部分抽象出来，使变化部分与主类分离开来，从而将多个维度的变化彻底分离。最后，提供一个管理类来组合不同维度上的变化，通过这种组合来满足业务的需要。
 * <p/>
 * Bridge模式是构造型的设计模式之一。Bridge模式基于类的最小设计原则，通过使用封装，聚合以及继承等行为来让不同的类承担不同的责任。它的主要特点是把抽象（abstraction）与行为实现（implementation
 * ）分离开来，从而可以保持各部分的独立性以及应对它们的功能扩展。
 *
 * @author pyx
 * @date 2021/12/28
 */
public class App {

    public static void main(String[] args) {
        Road road1 = new Highway(new Sedan());
        road1.run();
        Road road2 = new Highway(new SUV());
        road2.run();
        double[] d = new double[2];
        Road road3 = new Street(new Sedan());
        road3.run();
        Road road4 = new Street(new SUV());
        road4.run();

        //增强
        Person p1 = new Man(new Highway(new Sedan()));
        p1.work();

        Person p2 = new Woman(new Highway(new SUV()));
        p2.work();
    }
}
