package org.pyx.pyx.argorithm.shuffle;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;
import org.pyx.pyx.argorithm.sort.ArrayHelper;

/**
 * https://www.zhihu.com/question/50649396
 * <p>
 * 有点像https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle，但是这个算法需要开辟O(N)的空间，这里是优化的版本
 * <p>
 * 算法导论第五章，先生成一个从1到N的数组A然后下面图中中间伪代码部分描述的算法
 * <p>
 * swap A[i] with A[i+random()*(N-i)]
 *
 * @author pyx
 * @date 2022/8/24
 */
public class SimpleShuffle {

    /**
     * copy from {@link java.util.Collections.shuffle();}
     * @param nums
     */
    public void shuffle(int[] nums) {
        Random rnd = new Random();
        for (int i = nums.length; i > 1; i--) {
            swap(nums, i - 1, rnd.nextInt(i));
            System.out.println(Arrays.toString(nums));
        }
    }

    /**
     *
     * 
     * @param nums
     */
    public void shuffle2(int[] nums){
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int r = i + (int)(Math.random() * (n - i)); // between i and n-1
            swap(nums,i,r);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // take as input an array of strings and print them out to standard output
    public static void show(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            System.out.print(" ");
        }
    }

    @Test
    public void test(){

        int[] nums = ArrayHelper.getShuffledArray(10);
        System.out.println(Arrays.toString(nums));
        shuffle(nums);
        System.out.println(Arrays.toString(nums));

        int[] a = ArrayHelper.getShuffledArray(10);
        System.out.println(Arrays.toString(a));

        // shuffle array and print permutation
        shuffle(a);
        show(a);

        System.out.println();
        // do it again
        shuffle(a);
        show(a);

        System.out.println();
        // do it again
        shuffle2(a);
        show(a);
    }

    @Test
    public void test1(){
        int[] nums = ArrayHelper.getShuffledArray(10);
        System.out.println(Arrays.toString(nums));
        shuffle(nums);
        System.out.println(Arrays.toString(nums));
    }
}
