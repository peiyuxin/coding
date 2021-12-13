package org.pyx.apache.commons.collections;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;

/**
 * 1）缩短字符
 * 2）打印heading
 * 3）折行
 *
 * @author pyx
 * @date 2021/12/13
 */
public class StingTest {

    @Test
    public void testAbbreviate() {
        String str = "abcedfghijklmn";
        System.out.println(StringUtils.abbreviate(str, 6));
        System.out.println(StringUtils.abbreviate(str, "..", 0, 6));
    }

    @Test
    public void testRepeatAndCenter() {
        String stars = StringUtils.repeat("*", 20);
        String centered = StringUtils.center(" hello ", 20, "*");
        String heading = StringUtils.join(new Object[] {stars, centered, stars}, "\n");
        System.out.println(heading);
    }

    @Test
    public void testWrap() {
        String str = "abcedfghijklmn";
        System.out.println(WordUtils.wrap(str, 10, "\n", true));
    }
}
