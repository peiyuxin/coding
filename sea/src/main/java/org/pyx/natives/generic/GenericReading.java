package org.pyx.natives.generic;

import java.util.Arrays;
import java.util.List;

import org.pyx.natives.generic.bean.Apple;
import org.pyx.natives.generic.bean.Fruit;

/**
 * 错误实例
 * @author pyx
 * @date 2018/7/19
 */
public class GenericReading {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());
    static class Reader<T> {
        T readExact(List<T> list) {
            return list.get(0);
        }
    }
    static void f1() {
        Reader<Fruit> fruitReader = new Reader<Fruit>();
        // Errors: List<Fruit> cannot be applied to List<Apple>.
        // Fruit f = fruitReader.readExact(apples);
    }
    public static void main(String[] args) {
        f1();
    }
}