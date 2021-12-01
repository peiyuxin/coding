package org.pyx.apache.commons.collections;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;
import org.junit.Test;

/**
 * @author pyx
 * @date 2021/12/1
 */
public class BagTest {


    @Test
    public void testHashBag() {
        Bag bag = new HashBag();
        bag.add("a", 2);
        bag.add("A");
        bag.add("a");
        bag.add("b");
        bag.add("c");
        bag.add("c");
        System.out.println(bag);
        System.out.println(bag.getCount("A"));
        System.out.println(bag.getCount("a"));
        System.out.println(bag.uniqueSet());
    }
}
