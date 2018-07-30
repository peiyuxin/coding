package org.pyx.guava.base;

import com.google.common.base.Ascii;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author pyx
 * @date 2018/7/22
 */
public class AsciiTest {
    /**
     * The Unicode points {@code 00c1} and {@code 00e1} are the upper- and lowercase forms of
     * A-with-acute-accent, {@code Á} and {@code á}.
     */
    private static final String IGNORED = "`10-=~!@#$%^&*()_+[]\\{}|;':\",./<>?'\u00c1\u00e1\n";

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Test
    public void testToLowerCase() {

        assertEquals(LOWER, Ascii.toLowerCase(UPPER));
        assertSame(LOWER, Ascii.toLowerCase(LOWER));
        assertEquals(IGNORED, Ascii.toLowerCase(IGNORED));
        assertEquals("foobar", Ascii.toLowerCase("fOobaR"));
        throw new RuntimeException("");
    }

    @Test
    public void testToUpperCase() {
        assertEquals(UPPER, Ascii.toUpperCase(LOWER));
        assertSame(UPPER, Ascii.toUpperCase(UPPER));
        assertEquals(IGNORED, Ascii.toUpperCase(IGNORED));
        assertEquals("FOOBAR", Ascii.toUpperCase("fOobaR"));
    }

    @Test
    public void testCharsIgnored() {
        for (char c : IGNORED.toCharArray()) {
            String str = String.valueOf(c);
            assertTrue(str, c == Ascii.toLowerCase(c));
            assertTrue(str, c == Ascii.toUpperCase(c));
            assertFalse(str, Ascii.isLowerCase(c));
            assertFalse(str, Ascii.isUpperCase(c));
        }
    }

    @Test
    public void testCharsLower() {
        for (char c : LOWER.toCharArray()) {
            String str = String.valueOf(c);
            assertTrue(str, c == Ascii.toLowerCase(c));
            assertFalse(str, c == Ascii.toUpperCase(c));
            assertTrue(str, Ascii.isLowerCase(c));
            assertFalse(str, Ascii.isUpperCase(c));
        }
    }

    @Test
    public void testCharsUpper() {
        for (char c : UPPER.toCharArray()) {
            String str = String.valueOf(c);
            assertFalse(str, c == Ascii.toLowerCase(c));
            assertTrue(str, c == Ascii.toUpperCase(c));
            assertFalse(str, Ascii.isLowerCase(c));
            assertTrue(str, Ascii.isUpperCase(c));
        }
    }

    @Test
    public void testTruncate() {
        assertEquals("foobar", Ascii.truncate("foobar", 10, "..."));
        assertEquals("fo...", Ascii.truncate("foobar", 5, "..."));
        assertEquals("foobar", Ascii.truncate("foobar", 6, "..."));
        assertEquals("...", Ascii.truncate("foobar", 3, "..."));
        assertEquals("foobar", Ascii.truncate("foobar", 10, "…"));
        assertEquals("foo…", Ascii.truncate("foobar", 4, "…"));
        assertEquals("fo--", Ascii.truncate("foobar", 4, "--"));
        assertEquals("foobar", Ascii.truncate("foobar", 6, "…"));
        assertEquals("foob…", Ascii.truncate("foobar", 5, "…"));
        assertEquals("foo", Ascii.truncate("foobar", 3, ""));
        assertEquals("", Ascii.truncate("", 5, ""));
        assertEquals("", Ascii.truncate("", 5, "..."));
        assertEquals("", Ascii.truncate("", 0, ""));
    }

    @Test
    public void testTruncateIllegalArguments() {
        String truncated = null;
        try {
            truncated = Ascii.truncate("foobar", 2, "...");
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            truncated = Ascii.truncate("foobar", 8, "1234567890");
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            truncated = Ascii.truncate("foobar", -1, "...");
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            truncated = Ascii.truncate("foobar", -1, "");
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void testEqualsIgnoreCase() {
        assertTrue(Ascii.equalsIgnoreCase("", ""));
        assertFalse(Ascii.equalsIgnoreCase("", "x"));
        assertFalse(Ascii.equalsIgnoreCase("x", ""));
        assertTrue(Ascii.equalsIgnoreCase(LOWER, UPPER));
        assertTrue(Ascii.equalsIgnoreCase(UPPER, LOWER));
        // Create new strings here to avoid early-out logic.
        assertTrue(Ascii.equalsIgnoreCase(new String(IGNORED), new String(IGNORED)));
        // Compare to: "\u00c1".equalsIgnoreCase("\u00e1") == true
        assertFalse(Ascii.equalsIgnoreCase("\u00c1", "\u00e1"));
        // Test chars just outside the alphabetic range ('A'-1 vs 'a'-1, 'Z'+1 vs 'z'+1)
        assertFalse(Ascii.equalsIgnoreCase("@", "`"));
        assertFalse(Ascii.equalsIgnoreCase("[", "{"));
    }
}
