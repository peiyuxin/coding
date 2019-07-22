package org.pyx.pyx.argorithm.sort;

import org.junit.Test;

/**
 * 冒泡排序
 * <p/>
 * 效率最低的排序算法<br/>
 * 每一趟排序都做交换，把大的（或者小的）值往后排，这样经过一趟排序后，
 * 最大的值就跟泡泡一样浮到了最后面，经过n-i趟后，最后的i个元素是排序好的。
 * @author pyx
 * @date 2018/7/16
 */
public class BubbleSort extends SortAble{

    @Test
    public void test(){
        doTest();
    }

    @Override
    public void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length -i - 1; j++) {
                if(array[j] > array[j+1]){
                    //大值交换到后面
                    swap(array, j,j+1);
                }
            }
        }
    }
}
