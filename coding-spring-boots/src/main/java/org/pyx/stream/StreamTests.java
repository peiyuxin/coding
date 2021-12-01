package org.pyx.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * @author pyx
 * @date 2020/7/15
 */
public class StreamTests {
    public static void main(String[] args) throws Exception {
        Function<String,Boolean> myFilter= str ->{
            return str.startsWith("账财管理招人") && str.endsWith("！") && str.contains("持续");
        };
        Files.list(Paths.get("/Users/pyx/tmp/"))
            .filter(p -> p.getFileName().toString().endsWith("txt"))
            .flatMap(file -> {
                try {
                    return Files.lines(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(str -> myFilter.apply(str))
            .filter(str -> str.startsWith("账财管理招人"))
            .forEach(System.out::println);
    }
}

