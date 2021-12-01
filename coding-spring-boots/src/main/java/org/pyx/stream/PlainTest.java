package org.pyx.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

import org.junit.Test;

/**
 * @author pyx
 * @date 2020/7/16
 */
public class PlainTest {
    Function<String,Boolean> myFilter = new Function<String, Boolean>() {
        @Override
        public Boolean apply(String str) {
            return str.startsWith("账财管理招人") && str.endsWith("！") && str.contains("持续");
        }
    };

    @Test
    public void testPlain() throws Exception {

        new Thread(){
            @Override
            public void run(){
                File root = new File("/Users/pyx/tmp");
                File[] files = root.listFiles();
                for (File file : files) {
                    if (file.getName().endsWith("txt")) {
                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new FileReader(file));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            for (String line = reader.readLine(); line != null;
                                 line = reader.readLine()) {
                                if (line.startsWith("账财管理招人")) {
                                    System.out.println(line);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

    }

    /*********策略模式*********/

    interface Strategy extends Runnable {
        boolean selectFileWithName(String fileName);

        boolean selectLineWithContent(String fileName, String line);

        void processSelectedLine(String line);
    }

    public class FileIteratorStrategy implements Strategy {
        private String path;
        private Function<String, Boolean> filter;

        public FileIteratorStrategy(String path, Function<String, Boolean> filter) {
            this.path = path;
            this.filter = filter;
        }

        @Override
        public boolean selectFileWithName(String fileName) {
            return fileName.endsWith("txt");
        }

        @Override
        public boolean selectLineWithContent(String fileName, String line) {
            return filter.apply(line) && line.startsWith("账财管理招人");
        }

        @Override
        public void processSelectedLine(String line) {
            System.out.println(line);
        }

        @Override
        public void run() {
            File root = new File(path);
            File[] files = root.listFiles();
            for (File file : files) {
                if (selectFileWithName(file.getName())) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                            if (selectLineWithContent(file.getName(), line)) {
                                processSelectedLine(line);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Test
    public void testStrategy() throws Exception {
        Function<String,Boolean> myFilter = new Function<String, Boolean>() {
            @Override
            public Boolean apply(String str) {
                return str.startsWith("账财管理招人") && str.endsWith("！") && str.contains("持续");
            }
        };

        Strategy strategy = new FileIteratorStrategy("/tmp", myFilter);
        strategy.run();
    }
}
