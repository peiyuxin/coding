package org.pyx.guava.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Thread.currentThread;

/**
 * @author pyx
 * @date 2018/7/18
 */
public class BuckerTest {

    @Test
    public void test(){
        Bucket bucket = new Bucket();
        AtomicInteger DATA_CREATOR = new AtomicInteger(0);

        //生产线程 10个线程 每秒提交 50个数据  1/0.2s*10=50个
        IntStream.range(0,10).forEach(i -> {
            new Thread(() -> {
                for (; ;) {
                    int data = DATA_CREATOR.incrementAndGet();
                    try {
                        bucket.submit(data);
                        System.out.println("submit data");
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (Exception e) {
                        //对submit时，如果桶满了可能会抛出异常
                        if (e instanceof IllegalStateException) {
                            System.out.println(e.getMessage());
                            //当满了后，生产线程就休眠1分钟
                            try {
                                TimeUnit.SECONDS.sleep(60);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }

                        e.printStackTrace();
                    }
                }
            }).start();
        });

        //消费线程  采用RateLimiter每秒处理10个  综合的比率是5:1
        IntStream.range(0,10).forEach(i ->{
            new Thread(
                () -> {
                    for (;;){
                        bucket.cost(x ->{
                            System.out.println(currentThread()+"C.."+x);
                        });
                    }
                }
            ).start();
        });
    }
}
