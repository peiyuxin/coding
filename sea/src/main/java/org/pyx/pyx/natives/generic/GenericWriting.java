package org.pyx.pyx.natives.generic;

import java.util.ArrayList;
import java.util.List;

import org.pyx.pyx.natives.generic.bean.Apple;
import org.pyx.pyx.natives.generic.bean.Fruit;

/**
 * @author pyx
 * @date 2018/7/19
 */
public class GenericWriting {
    static List<Apple> apples = new ArrayList<>();
    static List<Fruit> fruits = new ArrayList<>();

    public static void main(String[] args) {
        f1();
        //f2();
        apples.forEach((i)->System.out.println(i));
        System.out.println("==================================");
        fruits.forEach((i)->System.out.println(i));
    }

    static void f1() {
        writeExact(apples, new Apple());
        writeExact(fruits, new Apple());
    }

    private static void f2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruits, new Apple());
    }
    static <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }

    static <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }

}
