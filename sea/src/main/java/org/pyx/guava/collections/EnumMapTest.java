package org.pyx.guava.collections;

import java.util.EnumMap;

/**
 * 创建EnumMap是必须指定一个枚举类，从而将该EnumMap和指定枚举类关联起来
 * @author pyx
 * @date 2018/7/17
 */
public class EnumMapTest {
    public static void main(String[] args) {
        EnumMap map = new EnumMap(Season.class);
        map.put(Season.SPRING,"春天");
        map.put(Season.FALL,"秋天");
        System.out.println(map);
    }
}
enum Season{
    SPRING,SUMMER,FALL,WINTER
}
