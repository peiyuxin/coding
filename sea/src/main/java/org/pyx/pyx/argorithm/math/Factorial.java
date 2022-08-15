package org.pyx.pyx.argorithm.math;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pyx
 * @date 2022/4/12
 */
public class Factorial {

    public static void main(String[] args) {
        System.out.println(factorial(5));
    }
    /**
     * 描述
     * 给一个数字 n, 以字符串的形式返回数字的阶乘
     */

    /*public String doFactorial(int n){
        return Math
    }*/
    /**
     * @param n: an integer
     * @return:  the factorial of n
     */
    public static String factorial(int n) {
        List<Integer> ans = new ArrayList<Integer>();
        ans.add(1);
        for(int i = 2;i <= n;i++){
            for(int j = 0;j<ans.size();j++){
                ans.set(j,ans.get(j) * i);
            }
            for(int j = 0; j< ans.size() -1;j++){
                ans.set(j+1,ans.get(j+1)+ans.get(j)/10);
                ans.set(j, ans.get(j)%10);
            }
            while (ans.get(ans.size()-1)>9){
                ans.add(ans.get(ans.size() -1)/10);
                ans.set(ans.size() -2,ans.get(ans.size() -2) % 10);
            }
        }
        String s = new String();
        for(int i = ans.size() -1 ;i>=0;i--){
            s += (char)(ans.get(i) + '0');
        }
        return s;
    }

    /**
    描述
    给定一个数n，返回该数的二阶阶乘。在数学中，正整数的二阶阶乘表示不超过这个正整数且与它有相同奇偶性的所有正整数乘积。

    结果一定不会超过long
    n是一个正整数
    */
    /**
     * @param n: the given number
     * @return:  the double factorial of the number
     */
    public long doubleFactorial(int n) {
        if (n <= 2) {
            return n;
        }
        return n * doubleFactorial(n - 2);
    }

}
