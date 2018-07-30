package org.pyx.guava.concurrent;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import static java.lang.Thread.currentThread;

/**
 * 漏桶
 * @author pyx
 * @date 2018/7/17
 */
public class Bucket{
     //Bucket<T extends Number> 做入参使用 ? extends Number
    private ConcurrentLinkedQueue<Integer> container = new ConcurrentLinkedQueue<>();
    //定义桶大小
    private static final int BUCKET_LIMIT =  1000;

    private final RateLimiter consumerRate = RateLimiter.create(10d);
    //往桶里面放数据时，确认没有超过桶的最大的容量
    private Monitor producerMonitor=new Monitor();

    //从桶里消费数据时，桶里必须存在数据
    private Monitor consumerMonitor=new Monitor();
    {
        List<Integer> list1=null;
        List<Number> list2=null;
        //rawType List generic Type Integer | Number

    }
    public void submit(Integer data){
        if(producerMonitor.enterIf(producerMonitor.newGuard(()->container.size() < BUCKET_LIMIT))){
            container.offer(data);
            System.out.println(currentThread() + " submit.." + data + " container size is :["+container.size()+"]");
        }else {
            //这里时候采用降级策略了。消费速度跟不上产生速度时，而且桶满了，抛出异常
            //或者存入MQ DB等后续处理
            System.err.println(currentThread().getName()+" the bucket is ful..");
            throw new IllegalStateException(currentThread().getName()+" the bucket is ful..Pls latter can try..");
        }
    }

    void ff(){
        Consumer<Integer> consumer=null;
        cost(consumer);
    }


    /**
     * 从桶里面消费数据
     * @param consumer
     */
    public void cost(Consumer<? extends Number> consumer){
        if (consumerMonitor.enterIf(consumerMonitor.newGuard(()->!container.isEmpty()))){
            try {
                //不打印时 写 consumerRate.acquire();
                System.out.println(currentThread()+"  waiting " + consumerRate.acquire());
                Integer data = container.poll();
                //container.peek() 只是去取出来不会删掉
                Consumer rawConsumer = consumer;
                rawConsumer.accept(data);
            }finally {
                consumerMonitor.leave();
            }
        }else {
            //当木桶的消费完后，可以消费那些降级存入MQ或者DB里面的数据
            System.out.println("will consumer Data from MQ...");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
