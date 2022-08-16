package org.pyx.pyx.argorithm.dac;

import org.junit.Test;

/**
 * 乘方a^n
 *
 * @author pyx
 * @date 2022/8/16
 */
public class Power {
    /**
     * 传统的做法,所谓的Naive algorithm）就是循环相乘n次，算法效率为O(n)
     * @param num
     * @param n
     * @return
     */
    public int power(int num, int n) {
        int result = 1;
        for (int i = 0; i < n; i++) {
            result *= num;
        }
        return result;
    }

    public int powerDAC(int num, int n){
        if (n == 0) {
            return 1;
        } else if (n ==1 ){
            return num;
        } else {
            if(n % 2 == 0) {
                return powerDAC(num, n / 2) * powerDAC(num, n/2);
            } else {
                return powerDAC(num,(n - 1)/2 * powerDAC(num,(n - 1)/2)) * num;
            }
        }
    }

    @Test
    public void test() {
        //assertThat(power(2, 2), Matchers.is(4));
        System.out.println(powerDAC(2,3));
        System.out.println(power(2,10));
    }
}
