package org.pyx.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.testing.NullPointerTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author pyx
 * @date 2018/7/22
 */
public class ObjectsTest {

    @Test
    public void testEqual() throws Exception {
        assertTrue(Objects.equal(1, 1));
        assertTrue(Objects.equal(null, null));

        // test distinct string objects
        String s1 = "foobar";
        String s2 = new String(s1);
        assertTrue(Objects.equal(s1, s2));

        assertFalse(Objects.equal(s1, null));
        assertFalse(Objects.equal(null, s1));
        assertFalse(Objects.equal("foo", "bar"));
        assertFalse(Objects.equal("1", 1));
        System.out.println(Objects.equal(s1,s2)+" "+(s1==s2));
    }

    @Test
    public void testHashCode() throws Exception {
        int h1 = Objects.hashCode(1, "two", 3.0);
        int h2 = Objects.hashCode(new Integer(1), new String("two"), new Double(3.0));
        // repeatable
        assertEquals(h1, h2);

        // These don't strictly need to be true, but they're nice properties.
        assertTrue(Objects.hashCode(1, 2, null) != Objects.hashCode(1, 2));
        assertTrue(Objects.hashCode(1, 2, null) != Objects.hashCode(1, null, 2));
        assertTrue(Objects.hashCode(1, null, 2) != Objects.hashCode(1, 2));
        assertTrue(Objects.hashCode(1, 2, 3) != Objects.hashCode(3, 2, 1));
        assertTrue(Objects.hashCode(1, 2, 3) != Objects.hashCode(2, 3, 1));
    }

    @GwtIncompatible // NullPointerTester
    @Test
    public void testNullPointers() {
        NullPointerTester tester = new NullPointerTester();
        tester.testAllPublicStaticMethods(Objects.class);
    }
}
