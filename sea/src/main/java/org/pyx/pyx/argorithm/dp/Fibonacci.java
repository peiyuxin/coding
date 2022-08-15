package org.pyx.pyx.argorithm.dp;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * Fibonacci递归、非递归、DP
 *
 * @author pyx
 * @date 2022/8/15
 */
public class Fibonacci {

    /**
     * 递归思想
     * @param n
     * @return
     */
    public int recursiveFibonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return recursiveFibonacci(n - 1) + recursiveFibonacci(n - 2);
    }

    /**
     * 非递归
     * @param n
     * @return
     */
    public int nonRecursiveFibonacci(int n) {
        int f1 = 1; //初始第二个，靠后
        int f2 = 1; //初始第一个，靠前
        int r = 0;
        if (n <= 2) {
            return 1;
        } else {
            for (int i = 3; i <= n; i++) {
                r = f1 + f2;
                f2 = f1;
                f1 = r;
            }
            return r;
        }
    }

    /**
     * 非递归2
     * @param n
     * @return
     */
    public int nonRecursiveFibonacci2(int n) {

        if (n <= 2) {
            return 1;
        }

        int[] dp =new int[]{0,1,1};
        for (int i = 2;i <= n;i++){
            dp[2] = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        return dp[2];
    }

    public int fibonacciWithDP(int n){
        int[] fib = new int[n+1];
        for (int i = 0; i < fib.length; i++) {
            if(i == 0){
                fib[i] = 0;
            } else if(i == 1 ){
                fib[i] = 1;
            } else {
                fib[i] = fib[i - 2] + fib[i-1];
            }
        }
        return fib[n];
    }

    @Test
    public void test(){
        assertThat(recursiveFibonacci(1), Matchers.is(1));
        System.out.println(fibonacciWithDP(10));
        System.out.println(nonRecursiveFibonacci(10));
        System.out.println(nonRecursiveFibonacci2(11));
        System.out.println(recursiveFibonacci(10));
    }

}
