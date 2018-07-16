package org.pyx.guava.collections;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

/**
 * @author pyx
 * @date 2018/7/16
 */
public class SetTest {


    public static void main(String[] args) {

        Set setA = Sets.newHashSet(1, 2, 3, 4, 5);
        HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);

        SetView<Integer> union = Sets.union(setA, setB);
        System.out.println("union:" + union);

        SetView<Integer> differenceA = Sets.difference(setA, setB);
        System.out.println("difference:" + differenceA);

        SetView<Integer> differenceB = Sets.difference(setB, setA);
        System.out.println("difference:" + differenceB);

        SetView<Integer> intersection = Sets.intersection(setA, setB);
        System.out.println("intersection:" + intersection);

        Set<String> strSet =  Sets.newIdentityHashSet();
        //只有key严格相等==才认为是同一个key
        strSet.add(new String("Spring"));
        strSet.add(new String("Spring"));
        System.out.println("IdentityHashSet:"+strSet);
        Set<Set<Integer>> setC= Sets.combinations(setA,3);
        //返回大小为size的set的所有子集的集合。
        for (Set<Integer> tmpSet:setC) {
            System.out.println(tmpSet);
        }
        System.out.println("combinations:" + setC);

    }
}
