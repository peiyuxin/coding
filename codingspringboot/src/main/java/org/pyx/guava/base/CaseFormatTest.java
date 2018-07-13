package org.pyx.guava.base;

import com.google.common.base.CaseFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pyx.code.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by pyx on 2018/7/13.
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
public class CaseFormatTest {

    @Test
    public void testLowerHyphenToLowerUnderscore() {
        assertEquals("foo", CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, "foo"));
        assertEquals("foo_bar", CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, "foo-bar"));
    }
    public void testLowerUnderscoreToUpperUnderscore() {
        if(true){
            throw new RuntimeException("aaa");
        }
        assertEquals("FOO", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, "foo"));
        assertEquals("FOO_BAR", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, "foo_bar"));
    }

    public void testLowerCamelToLowerHyphen() {
        assertEquals("foo", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "foo"));
        assertEquals("foo-bar", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "fooBar"));
        assertEquals("h-t-t-p", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "HTTP"));
    }
}
