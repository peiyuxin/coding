package org.pyx.argorithm.sort.heap;

import java.util.Arrays;

//import static java.util.Arrays.sort;

/**
 * 大顶堆排序
 * @author pyx
 * @date 2018/7/16
 */
public class MaxHeapSort {
    public static void main(String[] args) {
        int[] array = new int[]{7,9,4,3,0,5,2,8,1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    private static void sort(int[] data) {
        //构造最大堆
        buildMaxHeap(data);
        for (int i = data.length - 1; i > 0; i--) {
            // 循环，每次把根节点和最后一个节点调换位置
            exchange(data, 0, i);

            // 堆的长度减少1，排除置换到最后位置的根节点
            maxHeapify(data, 0, i);
        }
    }

    // 根据输入数组构建一个最大堆
    private static void buildMaxHeap(int[] data) {
        for (int i = data.length / 2 - 1; i >= 0; i--) {
            maxHeapify(data, i, data.length);
        }
        System.out.println("build max heap ok:");
        System.out.println(Arrays.toString(data));
    }

    /**
     * 堆调整使其生成最大堆
     * @param data
     * @param parentNodeIndex
     * @param heapSize 堆大小
     */
    private static void maxHeapify(int[] data,  int parentNodeIndex, int heapSize) {
        //左子节点索引
        int leftChildNodeIndex = parentNodeIndex * 2 + 1;
        //右子节点索引
        int rightChildNodeIndex = parentNodeIndex * 2 + 2;
        //最大节点索引
        int largestNodeIndex = parentNodeIndex;

        if(leftChildNodeIndex < heapSize && data[leftChildNodeIndex] > data[largestNodeIndex]){
            largestNodeIndex = leftChildNodeIndex;
        }
        if(rightChildNodeIndex < heapSize && data[rightChildNodeIndex] > data[largestNodeIndex]){
            largestNodeIndex = rightChildNodeIndex;
        }
        if(largestNodeIndex != parentNodeIndex){
            System.out.println("begin:"+Arrays.toString(data));
            exchange(data,parentNodeIndex,largestNodeIndex);
            System.out.println("after:"+Arrays.toString(data));
            System.out.println("=============================");
            maxHeapify(data,largestNodeIndex,heapSize);
        }
    }

    private static void exchange(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}
