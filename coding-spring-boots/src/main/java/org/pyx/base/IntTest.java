package org.pyx.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pyx
 * @date 2019/3/13
 */
public class IntTest {

    public static void main(String[] args) {
        Integer i = new Integer(100);
        i = Integer.MAX_VALUE;
        Integer j = new Integer(100);
        System.out.println(i == j);
        Lock lock = new ReentrantLock();
        Condition c = lock.newCondition();
        c.notify();
        int d = 321;
        int f = 321;
        Map map = new HashMap<>();
        Iterator<Entry<Integer, Integer>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Integer> entry = entries.next();
            entry.getValue();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

    }
}
