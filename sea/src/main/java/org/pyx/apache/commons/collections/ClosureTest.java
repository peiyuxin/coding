package org.pyx.apache.commons.collections;

import org.apache.commons.collections.Closure;
import org.junit.Test;
import org.pyx.guava.vo.Person;

/**
 * @author pyx
 * @date 2021/12/1
 */
public class ClosureTest {

    @Test
    public void testClosure(){
        Person p = new Person("bowen", 27);
        Closure closure = o -> ((Person) o).setAge(100);
        closure.execute(p);
        System.out.println(p);
    }
}
