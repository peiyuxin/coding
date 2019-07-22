package org.pyx.base;

import java.nio.charset.Charset;
import java.util.Arrays;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Charsets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author pyx
 * @date 2018/7/22
 */
public class CharsetsTest {
    @Test
    public void testUsAscii() {
        assertEquals(Charset.forName("US-ASCII"), Charsets.US_ASCII);
    }

    @Test
    public void testIso88591() {
        assertEquals(Charset.forName("ISO-8859-1"), Charsets.ISO_8859_1);
    }

    @Test
    public void testUtf8() {
        assertEquals(Charset.forName("UTF-8"), Charsets.UTF_8);
    }

    @Test
    public void testUtf16be() {
        assertEquals(Charset.forName("UTF-16BE"), Charsets.UTF_16BE);
    }

    @GwtIncompatible // Non-UTF-8 Charset
    public void testUtf16le() {
        assertEquals(Charset.forName("UTF-16LE"), Charsets.UTF_16LE);
    }

    @GwtIncompatible // Non-UTF-8 Charset
    public void testUtf16() {
        assertEquals(Charset.forName("UTF-16"), Charsets.UTF_16);
    }

    @Test
    public void testWhyUsAsciiIsDangerous() {
        byte[] b1 = "朝日新聞".getBytes(Charsets.US_ASCII);
        byte[] b2 = "聞朝日新".getBytes(Charsets.US_ASCII);
        byte[] b3 = "????".getBytes(Charsets.US_ASCII);
        byte[] b4 = "ニュース".getBytes(Charsets.US_ASCII);
        byte[] b5 = "スューー".getBytes(Charsets.US_ASCII);
        // Assert they are all equal (using the transitive property)
        assertTrue(Arrays.equals(b1, b2));
        assertTrue(Arrays.equals(b2, b3));
        assertTrue(Arrays.equals(b3, b4));
        assertTrue(Arrays.equals(b4, b5));
        System.out.println(new String(b1));
        System.out.println(new String(b2) +" "+ new String(b1).equals(new String(b2)));
        byte[] b11 = "朝日新聞".getBytes(Charsets.UTF_8);
        byte[] b12 = "聞朝日新".getBytes(Charsets.UTF_8);
        System.out.println(new String(b11));
        System.out.println(new String(b12) +" "+ new String(b11).equals(new String(b12)));
    }
}
