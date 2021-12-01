package org.pyx.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * @author pyx
 * @date 2020/7/15
 */
public class StreamTest {

    public static void execute(Function<String, Boolean> strategy) throws Exception {

    }

    public static void main(String[] args) throws IOException {
        Function<String,Boolean> myFilter= str ->{
            return str.startsWith("账") && str.endsWith("！") && str.contains("人");
        };

        Files.list(Paths.get("/Users/pyx/tmp/"))
            .filter(p -> p.getFileName().toString().endsWith("txt"))
            .forEach(p->System.out.println(p.getFileName()));

            /*

            .flatMap(file -> {
                System.out.print(file.getFileName());
                try {
                    return Files.lines(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            })*/
            //.filter(str -> myFilter.apply(str))
            //.map(str -> str.split("财"))
            //.forEach(System.out::println);


        /*Files.lines(Paths.get("/Users/pyx/tmp/jd.txt"), StandardCharsets.UTF_8)
            //.filter(str -> execute.apply(str))
            .filter(str -> myFilter.apply(str))
            //.filter(str -> str.startsWith("账"))
            //.filter(str -> str.endsWith("！"))
            //.filter(str -> str.contains("人"))
            //.map(str -> str.split(""))
            //.flatMap(str -> Arrays.stream(str))
            .forEach(System.out::println)*/
        ;
    }


}
/**
 * 1 赋值语句
 * 2 if语句
 * 3 for循环
 *
 * 代码的扩展和编排 策略模式
 */