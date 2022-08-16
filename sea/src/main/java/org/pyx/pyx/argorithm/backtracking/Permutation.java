package org.pyx.pyx.argorithm.backtracking;

import java.util.Arrays;

import org.junit.Test;

/**
 * 全排列问题，假定不重复。
 * <p>
 * 全排列的算法是一个基础，排列算法在它的基础上增加了选数过程（select），即先选后排。
 * 这里面主要是用到了一个递归的思想， 利用递归法来做这题关键下几点：
 * 1.普通情况-取出序列中的一个数并且将剩下的序列全排列
 * 2.特殊情况-序列中只剩一个数，则全排列就是其自身。将增个获得的序列输出。
 * 3.在不止一个数的情况下，该位要分别交换剩下的数（例如：两个数A，B 则有两种情况，一个是AB 一个是BA）
 * <p>
 * 全排列就是从第一个数字起每个数分别与它后面的数字交换。
 * <p>
 * 例如1,2,3
 * 步骤1.1）1和1交换（实际没动），然后加上2,3的全排列即可。
 * 步骤1.2）1和2交换，然后加上1,3的全排列即可。
 * 步骤1.3）1和3交换，然后加上2,1的全排列即可。
 * <p>
 * 计算后面的排列是一个递归的过程，例如计算2,3的排列。
 * 步骤2.1），2和2交换（实际没动），然后加上3的全排列即可，就是本身。
 * 步骤2.2），2和3交换，然后加上2的全排列即可，就是本身。
 * <p>
 * 所以步骤1.1加上子步骤2.1和2.2后，得出的结果如下：
 * [1, 2, 3]
 * [1, 3, 2]
 * <p>
 * 全排列的数学公式如下:
 * 从n个不同的元素中任取m个，按照一定的顺序排成一列，极为Pnm
 * 当n=m时称作全排列，记为Pnn
 * <p>
 * 排列数公式：
 * n!=1*2*3...*n, 规定0!=1
 * Pnm=n! / (n-m)! = n(n-1)(n-2)...(n-m+1)
 * Pnn=n!
 * <p>
 * 所以1,2,3的全排列有3!种，即6种
 * @author pyx
 * @date 2022/8/15
 */
public class Permutation {

    public void permutation(int[] nums){
        doPermutation(nums, 0, nums.length);
    }

    private void doPermutation(int[] nums, int m, int length) {
        if(m == length) {
            System.out.println(Arrays.toString(nums));
        } else {
            for (int i = m; i < length; i++) {
                swap(nums,i,m);
                doPermutation(nums,m+1, length);
                swap(nums,i,m);
            }
        }
    }
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j]= temp;
    }

    @Test
    public void test() {
        permutation(new int[]{1,2,3});
    }
}
