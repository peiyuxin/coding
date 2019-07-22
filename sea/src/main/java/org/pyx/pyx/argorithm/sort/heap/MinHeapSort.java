package org.pyx.pyx.argorithm.sort.heap;

import java.util.Arrays;

import org.junit.Test;
import org.pyx.pyx.argorithm.sort.SortAble;

/**
 * @author pyx
 * @date 2018/7/16
 */
public class MinHeapSort extends SortAble{

    @Test
    public void test(){
        doTest();
    }
    @Override
    protected void sort(int[] data) {
        //构造最小堆
        buildMinHeap(data);
        for (int i = data.length - 1; i > 0; i--) {
            // 循环，每次把根节点和最后一个节点调换位置
            swap(data, 0, i);

            // 堆的长度减少1，排除置换到最后位置的根节点
            minHeapify(data, 0, i);
        }
        System.err.println(Arrays.toString(data));

    }

    private void buildMinHeap(int[] data) {
        for (int i = data.length / 2 - 1; i >= 0; i--) {
            minHeapify(data, i, data.length);
        }
        System.out.println("build min heap ok:");
        System.out.println(Arrays.toString(data));
    }

    private void minHeapify(int[] data, int parentNodeIndex, int heapSize) {
        // 左子节点索引 2*i+1
        int leftChildNodeIndex = parentNodeIndex * 2 + 1;
        // 右子节点索引 2*i+2
        int rightChildNodeIndex = parentNodeIndex * 2 + 2;
        // 最小节点索引
        int smallestChildNodeIndex = parentNodeIndex;
        // 如果左子节点小于父节点，则将左子节点作为最小节点
        if(leftChildNodeIndex < heapSize && data[smallestChildNodeIndex] < data[parentNodeIndex]){
            smallestChildNodeIndex = leftChildNodeIndex;
        }

        // 如果右子节点比最小节点还小，那么最小节点应该是右子节点
        if(rightChildNodeIndex < heapSize && data[smallestChildNodeIndex] < data[parentNodeIndex]){
            smallestChildNodeIndex = rightChildNodeIndex;
        }

        // 最后，如果最小节点和父节点不一致，则交换他们的值
        if(smallestChildNodeIndex != parentNodeIndex){
            swap(data,parentNodeIndex,smallestChildNodeIndex);
            // 交换完父节点和子节点的值，对换了值的子节点检查是否符合最小堆的特性
            minHeapify(data,smallestChildNodeIndex,heapSize);
        }
    }

}
