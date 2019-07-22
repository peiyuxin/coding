package org.pyx.pyx.natives.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

/**
 * @author pyx
 * @date 2019/1/3
 */
public class LambdaSort {

    @Test
    public void testSort(){
        List<Apple> appleList = new ArrayList<>();
        Apple apple1 = new Apple(1,"g","2018-12");
        Apple apple2 = new Apple(2,"r","2018-01");
        Apple apple3 = new Apple(3,"b","2019-12");
        Apple apple4 = new Apple(4,"b","2019-02");

        appleList.add(apple4);
        appleList.add(apple2);
        appleList.add(apple3);
        appleList.add(apple1);
        System.out.println(appleList);
        System.out.println("============");
        appleList.sort(Comparator.comparing(Apple::getMonth));
        System.out.println(appleList);
        System.out.println("============");
        appleList.sort(Comparator.comparing(Apple::getId));
        System.out.println(appleList);
    }
}
