package org.pyx.pyx.natives.generic;

import java.util.ArrayList;
import java.util.List;

import org.pyx.pyx.natives.generic.bean.Fruit;

/**
 * @author pyx
 * @date 2018/7/19
 */
public class GenericsAndCovariance {
    public static void main(String[] args) {
        List<? extends Fruit> flist = new ArrayList<>();
        // Compile Error: can't add any type of object:原因是站在编译器角度,List<? extends Fruit> flist它自身可以有多种含义
        //当我们尝试add一个Orange的时候，flist可能指向new ArrayList<Apple>();
        //当我们尝试add一个Fruit的时候，这个Fruit可以是任何类型的Fruit，而flist可能只想某种特定类型的Fruit，编译器无法识别所以会报错。
        // flist.add(new Apple());
        // flist.add(new Orange());
        // flist.add(new Fruit());
        //flist.add(new Object());
        flist.add(null);
        // We Know that it returns at least Fruit:
        System.out.println(flist.get(0));
    }
}
