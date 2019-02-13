package org.pyx.natives.concurrent.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @author pyx
 * @date 2018/9/14
 */
public class UpdateSorterMain {

    public static void main(String[] args) {
        List<Update> lu = new ArrayList<Update>();
        String text = "";
        final Update.Builder ub = new Update.Builder();
        final Author a = new Author("Tallulah");

        for (int i = 0; i < 256; i++) {
            text = text + "X";
            long now = System.currentTimeMillis();
            lu.add(ub.setAuthor(a).setText(text).setCreateTime(now).build());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
        Collections.shuffle(lu);
        Update[] updates = lu.toArray(new Update[0]); // Avoid allocation by passing
        // zero-sized array
        MicroBlogUpdateSorter sorter = new MicroBlogUpdateSorter(updates);
        ForkJoinPool pool = new ForkJoinPool(4);
        pool.invoke(sorter);

        Arrays.stream(sorter.getResult()).map(Update::getText).forEach(System.out::println);
    }
}
