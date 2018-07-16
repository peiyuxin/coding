package org.pyx.natives.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * @author pyx
 * @date 2018/7/17
 */
public class LambdaExpression {
    @Test
    public void test(){
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(System.out::println);
        System.out.println("==================");
        features.forEach((str) -> System.out.println(str));
    }

}
