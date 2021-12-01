package org.pyx.apache.commons.lang3;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

/**
 * @author pyx
 * @date 2021/12/1
 */
public class ArrayTest {

    @Test
    public void testToString(){
        int[] ints = new int[] {1, 2, 3, 4, 5};
        System.out.println(ints); // [I@368102c8 printing address...
        System.out.println(ArrayUtils.toString(ints));// {1,2,3,4,5}
    }
}
