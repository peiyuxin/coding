package org.pyx.pyx.natives.generic;

import java.util.Arrays;
import java.util.List;

import org.pyx.pyx.natives.generic.bean.Apple;
import org.pyx.pyx.natives.generic.bean.Fruit;

/**
 * 解决编译器无法识别类集成的问题,通过使用通配符解决问题
 * 协变式阅读器
 * @author pyx
 * @date 2018/7/19
 */
public class CovariantReader<T> {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());
    T readCovariant(List<? extends T> list) {
        return list.get(0);
    }

    private static void f2() {
        CovariantReader<Fruit> reader = new CovariantReader<>();
        Fruit f = reader.readCovariant(apples);
        Fruit a = reader.readCovariant(fruit);
        System.out.println(f);
        System.out.println(a);
    }

    public static void main(String[] args) {
        f2();
    }

}
