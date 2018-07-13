package org.pyx.code;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by pyx on 2018/7/13.
 */
@SpringBootApplication
public class Application implements CommandLineRunner{

    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("just for fun");
    }

}
