package org.pyx.pyx.argorithm.array;

import org.junit.Test;

/**
 * 硬币找零问题
 * {@link #coinChange(int[], int[], int)}是一个接受最小硬币为1的解。
 * {@link #coinChange2(int[], int)}是leetcode上要求找不到返回-1的算法
 * @see https://leetcode.com/problems/coin-change/
 * @author pyx
 * @date 2022/8/15
 */
public class CoinChange {

    /**
     * 硬币找零：动态规划算法
     * <p>
     * 当3元时候，需要计算去掉1元硬币和3元硬币后的最少数量+1，即d(3)=min{d(3-1)+1, d(3-3)+1}。
     * 1元和3元的挑选是所有的面值当中不小于当前要换的钱的几个值。
     * <pre>
     *     d(i) = Min{ d(i-Vj) + 1 }，其中i-Vj >=0，Vj表示第j个硬币的面值;
     * </pre>
     *
     * @param minCoinChanges 保存面值为i的纸币找零所需的最小硬币数
     * @param coins          保存每一种硬币的币值的数组
     * @param money          需要找零的面值
     */
    public static void coinChange(int[] minCoinChanges, int[] coins, int money){
        //零钱种类数量
        int coinsNum = coins.length;
        //0元需要0个硬币，初始值
        minCoinChanges[0] = 0;
        //对每一分钱都找零，即保存子问题的解以备用，即填表
        for (int cents = 0; cents <= money; cents++) {
            //当用最小币值的硬币找零时，所需硬币数量最多
            int minCoins = cents;

            if(cents== 19){
                int b=1;
            }

            // 遍历每一种面值的硬币，看是否可作为找零的其中之一
            for (int kind = 0; kind < coinsNum; kind++){
                //若当前面值的硬币小于当前的cents则分解问题并查表
                if(coins[kind] <= cents){
                    int temp = minCoinChanges[cents-coins[kind]] + 1;
                    if(temp < minCoins){
                        minCoins = temp;
                    }
                }
            }
            // 保存最小硬币数
            minCoinChanges[cents] = minCoins;
            System.out.println("面值为："+cents+"的最小硬币数：" + minCoinChanges[cents]);
        }
    }

    public int coinChange2(int[] coins, int amount) {
        return -1;
    }

    @Test
    public void test() {
        // 硬币面值预先已经按降序排列
        int[] coins = new int[]{1,2,5,10};
        // 需要找零的面值
        int money = 19 ;
        // 保存每一个面值找零所需的最小硬币数，0号单元舍弃不用，所以要多加1
        int[] minCoinChanges = new int[money + 1];
        coinChange(minCoinChanges, coins, money);

    }
}
