package org.pyx.natives.generic;

import org.junit.Test;

/**
 * @author pyx
 * @date 2018/7/19
 */
public class GenericTest {

    @Test
    public void test1(){

        Box<String> name = new Box<String>("corn");
        Box<Integer> age = new Box<Integer>(712);
        Box<Number> number = new Box<Number>(314);
        getData(name);
        getData(age);
        getData(number);

        //getUpperNumberData(name); // 1
        getUpperNumberData(age);    // 2
        getUpperNumberData(number); // 3

        getSupperData(age);
        getSupperData(number);
    }

    private void getUpperNumberData(Box<? extends Number> data) {
        System.out.println("data1 :" + data.getData());
    }

    private void getData(Box<?> data) {
        System.out.println("data2 :" + data.getData());
    }

    private void getSupperData(Box<? super Integer> data) {
        System.out.println("data3 :" + data.getData());
    }
}
