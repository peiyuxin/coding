package org.pyx.pyx.argorithm.shuffle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.primitives.Ints;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.pyx.pyx.argorithm.sort.ArrayHelper;

/**
 * 完美洗牌问题
 * <p>
 * 有个长度为2n的数组{a1,a2,a3,...,an,b1,b2,b3,...,bn}，希望排序后{a1,b1,a2,b2,....,an,bn}，请考虑有无时间复杂度o(n)，空间复杂度0(1)的解法。
 * <p>
 * https://github.com/julycoding/The-Art-Of-Programming-By-July/blob/master/ebook/zh/02.09.md
 *
 * @author pyx
 * @date 2022/8/25
 */
public class PerfectShuffle {

    public void shuffle(int[] a,int n){
        int i,t, n2 = n*2;
        perfectShuffle2(a, n);
        for (i = 2; i <= n2 ; i+=2) {
            t = a[i -1 ];
            a[i - 1] = a[i];
            a[i] = t;
        }
    }

    //时间O(n),空间O（1）

    /**
     * step 1 找到 2 * m = 3^k - 1 使得 3^k <= 2 * n < 3^(k +1)
     *
     * step 2 把a[m + 1..n + m]那部分循环移m位
     *
     * step 3 对每个i = 0,1,2..k - 1，3^i是个圈的头部，做cycle_leader算法，数组长度为m，所以对2 * m + 1取模。
     *
     * step 4 对数组的后面部分A[2 * m + 1.. 2 * n]继续使用本算法, 这相当于n减小了m。
     * @param a
     * @param n
     */
    private void perfectShuffle2(int[] a, int n) {
        int n2, m,i,k,t;
        for (; n>1;){
            //step 1
            n2 = n*2;
            for (k =0,m = 1;n2 / m >= 3;++k, m *= 3){

            }
            m /= 2;
            // 2m =3^k -1, 3^k <=2n<3^(k + 1)

            //step 2
            int [] tmp1 = Arrays.copyOfRange(a,m,n+m);
            rightRotate(tmp1, m, n);
            //step 3
            for (i =0, t=1; i < k; ++i, t*=3){
                cycleLeader(a, t, m * 2 + 1);
            }

            //step 4
            int [] tmp2 = Arrays.copyOfRange(a,2*m,2*n);
            rightRotate(tmp2, 2*m, 2*n);

            //a += m * 2;

            a = ArrayUtils.addAll(tmp1,tmp2);
            n -= m;
        }
        //n = 1
        t = a[1];
        a[1] = a[2];
        a[2] = t;
    }

    //数组下标从1开始，from是圈的头部，mod是要取模的数 mod 应该为 2 * n + 1，时间复杂度O(圈长）
    private void cycleLeader(int[] nums, int from, int mod) {
        int t,i;

        for (i = from * 2 % mod; i != from; i = i * 2 % mod)
        {
            t = nums[i];
            nums[i] = nums[from];
            nums[from] = t;
        }
    }

    //循环右移num位 时间复杂度O(n)
    private void rightRotate(int[] a, int num, int n) {
        reverse(a, 1, n - num);
        reverse(a, n - num + 1, n);
        reverse(a, 1, n);
    }

    void reverse(int nums[], int from, int to) {
        int t;
        for (; from < to; ++from, --to)
        {
            t = nums[from];
            nums[from] = nums[to];
            nums[to] = t;
        }
    }

    public void shuffle1(int[] nums) {
        //TODO no need?
        if (nums.length % 2 != 0) {
            throw new IllegalArgumentException("Odd length expected");
        }
        int[] temp = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; i++) {
            temp[(i * 2) % (nums.length + 1)] = nums[i - 1];
        }
        for (int i = 1; i <= nums.length; i++) {
            nums[i - 1] = temp[i];
        }
    }

    @Test
    public void test() {
        int[] nums = getShuffledArray(10);
        System.out.println(Arrays.toString(nums));
        shuffle(nums,nums.length/2);
        System.out.println(Arrays.toString(nums));
    }

    public static int[] getShuffledArray(int len){
        List<Integer> list = new ArrayList<>(len);
        for (int i =0;i < len; i++){
            list.add(i);
        }
        Collections.shuffle(list);
        return Ints.toArray(list);
    }

}
