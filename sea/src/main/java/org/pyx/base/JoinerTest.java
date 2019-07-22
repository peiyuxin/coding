package org.pyx.base;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * @author pyx
 * @date 2018/7/24
 */
public class JoinerTest {
    private static final Joiner J = Joiner.on("-");

    // <Integer> needed to prevent warning :(
    private static final Iterable<Integer> ITERABLE_ = Arrays.<Integer>asList();
    private static final Iterable<Integer> ITERABLE_1 = Arrays.asList(1);
    private static final Iterable<Integer> ITERABLE_12 = Arrays.asList(1, 2);
    private static final Iterable<Integer> ITERABLE_123 = Arrays.asList(1, 2, 3);
    private static final Iterable<Integer> ITERABLE_NULL = Arrays.asList((Integer) null);
    private static final Iterable<Integer> ITERABLE_NULL_NULL = Arrays.asList((Integer) null, null);
    private static final Iterable<Integer> ITERABLE_NULL_1 = Arrays.asList(null, 1);
    private static final Iterable<Integer> ITERABLE_1_NULL = Arrays.asList(1, null);
    private static final Iterable<Integer> ITERABLE_1_NULL_2 = Arrays.asList(1, null, 2);
    private static final Iterable<Integer> ITERABLE_FOUR_NULLS =
        Arrays.asList((Integer) null, null, null, null);

    @Test
    public void test() {
        //Join string
        String[] array = new String[] {"a", "b", null, "null", "c", "d"};
        String joinedStr = Joiner.on(",").skipNulls().join(array);
        System.out.println(joinedStr);
        System.out.println(uglyJoin(array));

        //Join map
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        String joinedMapStr = Joiner.on(",").useForNull("this is null!!").withKeyValueSeparator("|").join(map);
        System.out.println(joinedMapStr);
    }

    @Deprecated
    public static String uglyJoin(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                sb.append(array[i]).append(",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Test
    public void testNoSpecialNullBehavior() {
        checkNoOutput(J, ITERABLE_);
        checkResult(J, ITERABLE_1, "1");
        checkResult(J, ITERABLE_12, "1-2");
        checkResult(J, ITERABLE_123, "1-2-3");

        try {
            J.join(ITERABLE_NULL);
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            J.join(ITERABLE_1_NULL_2);
            fail();
        } catch (NullPointerException expected) {
        }

        try {
            J.join(ITERABLE_NULL.iterator());
            fail();
        } catch (NullPointerException expected) {
        }
        try {
            J.join(ITERABLE_1_NULL_2.iterator());
            fail();
        } catch (NullPointerException expected) {
        }
    }

    private static void checkResult1(Joiner joiner, Iterable<Integer> parts, String expected) {
        assertEquals(expected, joiner.join(parts));
        assertEquals(expected, joiner.join(parts.iterator()));

        StringBuilder sb1FromIterable = new StringBuilder().append('x');
        joiner.appendTo(sb1FromIterable, parts);
        assertEquals("x" + expected, sb1FromIterable.toString());

        StringBuilder sb1FromIterator = new StringBuilder().append('x');
        joiner.appendTo(sb1FromIterator, parts.iterator());
        assertEquals("x" + expected, sb1FromIterator.toString());

        Integer[] partsArray = Lists.newArrayList(parts).toArray(new Integer[0]);
        assertEquals(expected, joiner.join(partsArray));

        StringBuilder sb2 = new StringBuilder().append('x');
        joiner.appendTo(sb2, partsArray);
        assertEquals("x" + expected, sb2.toString());

        int num = partsArray.length - 2;
        if (num >= 0) {
            Object[] rest = new Integer[num];
            for (int i = 0; i < num; i++) {
                rest[i] = partsArray[i + 2];
            }

            assertEquals(expected, joiner.join(partsArray[0], partsArray[1], rest));

            StringBuilder sb3 = new StringBuilder().append('x');
            joiner.appendTo(sb3, partsArray[0], partsArray[1], rest);
            assertEquals("x" + expected, sb3.toString());

        }
    }

    private static void checkResult(Joiner joiner, Iterable<Integer> parts, String expected) {
        assertEquals(expected, joiner.join(parts));
        assertEquals(expected, joiner.join(parts.iterator()));

        StringBuilder sb1FromIterable = new StringBuilder().append('x');
        joiner.appendTo(sb1FromIterable, parts);
        assertEquals("x" + expected, sb1FromIterable.toString());

        StringBuilder sb1FromIterator = new StringBuilder().append('x');
        joiner.appendTo(sb1FromIterator, parts.iterator());
        assertEquals("x" + expected, sb1FromIterator.toString());

        Integer[] partsArray = Lists.newArrayList(parts).toArray(new Integer[0]);
        assertEquals(expected, joiner.join(parts));

        StringBuilder sb2 = new StringBuilder().append('x');
        joiner.appendTo(sb2, partsArray);
        assertEquals("x" + expected, sb2.toString());
        System.out.println(sb2.toString());

        int num = partsArray.length - 2;
        if (num >= 0) {
            Object[] rest = new Integer[num];
            for (int i = 0; i < num; i++) {
                rest[i] = partsArray[i + 2];
            }

            assertEquals(expected, joiner.join(partsArray[0], partsArray[1], rest));
            System.out.println(expected +" === "+ joiner.join(partsArray[0], partsArray[1], rest));

            StringBuilder sb3 = new StringBuilder().append('x');
            joiner.appendTo(sb3, partsArray[0], partsArray[1], rest);
            assertEquals("x" + expected, sb3.toString());

        }
    }

    private static void checkNoOutput(Joiner joiner, Iterable<Integer> set) {
        assertEquals("", joiner.join(set));
        assertEquals("", joiner.join(set.iterator()));

        Object[] array = Lists.newArrayList(set).toArray(new Integer[0]);
        assertEquals("", joiner.join(array));

        StringBuilder sb1FromIterable = new StringBuilder();
        assertSame(sb1FromIterable, joiner.appendTo(sb1FromIterable, set));
        assertEquals(0, sb1FromIterable.length());

        StringBuilder sb1FromIterator = new StringBuilder();
        assertSame(sb1FromIterator, joiner.appendTo(sb1FromIterator, set));
        assertEquals(0, sb1FromIterator.length());

        StringBuilder sb2 = new StringBuilder();
        assertSame(sb2, joiner.appendTo(sb2, array));
        assertEquals(0, sb2.length());

        try {
            joiner.appendTo(NASTY_APPENDABLE, set);
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        try {
            joiner.appendTo(NASTY_APPENDABLE, set.iterator());
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        try {
            joiner.appendTo(NASTY_APPENDABLE, array);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void test_skipNulls_useForNull() {
        Joiner j = Joiner.on("x").skipNulls();
        try {
            j = j.useForNull("y");
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    @Test
    public void test_useForNull_twice() {
        String[] array = new String[] {"a", "b", null, "null", "c", "d"};
        Joiner j = Joiner.on("x").useForNull("y");
        try {
            j = j.useForNull("y");
            System.out.println(j.join(array));
            fail();
        } catch (UnsupportedOperationException expected) {
            System.err.println(expected);
            expected.fillInStackTrace();
        }
    }

    @Test
    public void testMap() {
        MapJoiner j = Joiner.on(';').withKeyValueSeparator(':');
        assertEquals("", j.join(ImmutableMap.of()));
        assertEquals(":", j.join(ImmutableMap.of("", "")));

        Map<String, String> mapWithNulls = Maps.newLinkedHashMap();
        mapWithNulls.put("a", null);
        mapWithNulls.put(null, "b");

        try {
            j.join(mapWithNulls);
            fail();
        } catch (NullPointerException expected) {
        }

        assertEquals("a:00;00:b", j.useForNull("00").join(mapWithNulls));

        StringBuilder sb = new StringBuilder();
        j.appendTo(sb, ImmutableMap.of(1, 2, 3, 4, 5, 6));
        assertEquals("1:2;3:4;5:6", sb.toString());
    }

    private static final Appendable NASTY_APPENDABLE = new Appendable() {
        @Override
        public Appendable append(CharSequence csq) throws IOException {
            throw new IOException();
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) throws IOException {
            throw new IOException();
        }

        @Override
        public Appendable append(char c) throws IOException {
            throw new IOException();
        }
    };
}
