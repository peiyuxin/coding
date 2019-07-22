package org.pyx.pyx.argorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.primitives.Ints;

/**
 * 排序数组辅助类
 * @author pyx
 * @date 2018/7/16
 */
public class ArrayHelper {
    /**
     *
     * @param len return arrayLength
     * @return array not in order and elements are not duplicate
     */
    public static int[] getShuffledArray(int len){
        List<Integer> list = new ArrayList<>(len);
        for (int i =0;i < len; i++){
            list.add(i);
        }
        Collections.shuffle(list);
        return Ints.toArray(list);
    }

    /**
     *
     * @param len
     * @return array in order and elements are not duplicate
     */
    public static int[] getContinuousArray(int len){
        List<Integer> list = new ArrayList<>(len);
        for (int i =0;i < len; i++){
            list.add(i);
        }
        return Ints.toArray(list);
    }
}