package org.pyx.guava.base;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.truth.Truth;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * @author pyx
 * @date 2018/7/24
 */
public class SplitterTest {
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');

    @Test
    public void testCharacterSimpleSplit() {
        String simple = "a,b,c";
        Iterable<String> letters = COMMA_SPLITTER.split(simple);
        System.out.println(letters);
    }

    @Test
    public void testCharacterSimpleSplitToList() {
        String simple = "a,b,c";
        List<String> letters = COMMA_SPLITTER.limit(2).splitToList(simple);
        System.out.println(letters); //[a, b,c] 有limit所有在第二个的时候停止了split
    }

    @Test
    public void testCharacterSplitWithTrim() {
        String jacksons = "arfo(Marlon)aorf  , (Michael)or fa, afro(Jackie)orfa, "
            + "ofar(Jemaine), aff(Tito)";
        Iterable<String> family = COMMA_SPLITTER
            .trimResults(CharMatcher.anyOf("afro").or(CharMatcher.WHITESPACE))
            .split(jacksons);
        System.out.println(family);
    }

    @Test
    public void testPatternSplitMatchingIsGreedy() {
        String longDelimiter = "a, b,   c";
        Iterable<String> letters = Splitter.on(Pattern.compile(",\\s*"))
            .split(longDelimiter);
        System.out.println(letters);
    }

    @Test
    public void testStringSplitWithDoubleDelimiterOmitEmptyStrings() {
        String doubled = "a..b.c";
        Iterable<String> letters = Splitter.on('.')
            .omitEmptyStrings().split(doubled);
        System.out.println(letters); //[a, b, c]
    }

    @Test
    public void testMapSplitter_trimmedBoth() {
        Map<String, String> m = COMMA_SPLITTER
            .trimResults()
            .withKeyValueSeparator(Splitter.on(':').trimResults())
            .split("boy  : tom , girl: tina , cat  : kitty , dog: tommy ");
        ImmutableMap<String, String> expected =
            ImmutableMap.of("boy", "tom", "girl", "tina", "cat", "kitty", "dog", "tommy");
        assertThat(m, is(expected));
        System.out.println(m);
        System.out.println(expected);
    }

    @Test
    public void testToString() {
        assertEquals("[]", COMMA_SPLITTER.split("").toString());
        assertEquals("[a, b, c]", COMMA_SPLITTER.split("a,b,c").toString());
        assertEquals("[yam, bam, jam, ham]", Splitter.on(", ").split("yam, bam, jam, ham").toString());

        System.out.println(COMMA_SPLITTER.split("").toString());
        System.out.println(COMMA_SPLITTER.split("a,b,c".toString()));
        Iterable<String> iterable = Splitter.on(", ").split("yam, bam,jam, ham");
        System.out.println(iterable.toString());
        List<String> myList = Lists.newArrayList(iterable);
        iterable.forEach(System.out::println);
        System.out.println(myList.size());
    }

    @Test
    public void testCharacterSimpleSplitWithNoDelimiter() {
        String simple = "a,b,c";
        Iterable<String> letters = Splitter.on('.').split(simple);
        Truth.assertThat(letters).containsExactly("a,b,c").inOrder();
    }
}
