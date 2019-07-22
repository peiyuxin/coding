package org.pyx.pyx.natives.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

/**
 * @author pyx
 * @date 2018/7/17
 */
public class LambdaExpression {
    @Test
    public void test(){
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time");
        features.forEach(System.out::println);
        System.out.println("==================");
        features.forEach((str) -> System.out.println(str));
    }

    @Test
    public void test1(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        //为什么需要强制转换？
        filter(languages, (str)->((String)str).startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str)->((String)str).endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str)->true);

        System.out.println("Print no language : ");
        filter(languages, (str)->false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str)->((String)str).length() > 4);
    }
    public void filter1(List<String> names, Predicate condition) {
        for(String name: names)  {
            if(condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }
    public static void filter(List names, Predicate condition) {
        names.stream().filter((name) -> (condition.test(name)))
            .forEach((name) -> {System.out.println(name + " ");
            });
    }
}
