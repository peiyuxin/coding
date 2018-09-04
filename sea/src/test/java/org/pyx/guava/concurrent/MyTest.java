package org.pyx.guava.concurrent;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * @author pyx
 * @date 2018/8/29
 */
public class MyTest {
    @Test
    public void testAddDouble() {
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);

        BigDecimal b = new BigDecimal(1111111111.77777);
        System.out.println(b.floatValue());
    }
}
