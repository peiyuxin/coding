package org.pyx.pyx.argorithm.sort;

import java.util.Arrays;

import org.hamcrest.Matchers;

import static org.junit.Assert.assertThat;

/**
 * @author pyx
 * @date 2018/7/16
 */
public abstract class SortAble {
    protected abstract void sort(int[] array);

    protected void swap(int[] array, int i,int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    protected void doTest(){
        int[] array = ArrayHelper.getShuffledArray(9);
        System.out.println(Arrays.toString(array));
        sort(array);
        System.out.println(Arrays.toString(array));
        assertThat(array, Matchers.is(ArrayHelper.getContinuousArray(9)));

        array = ArrayHelper.getShuffledArray(81);
        System.out.println(Arrays.toString(array));
        sort(array);
        System.out.println(Arrays.toString(array));
        assertThat(array, Matchers.is(ArrayHelper.getContinuousArray(81)));
    }
    protected void doReversedTest(){

    }
}
