package org.pyx.argorithm.sort.heap;

import java.util.ArrayList;

/**
 * @author pyx
 * @date 2018/7/16
 */
public class MinimumHeap<T extends Comparable<T>> {
    ArrayList<T> items;
    //用于计数
    int cursor;
    public MinimumHeap(int size){
        items = new ArrayList<T>(size);
        cursor = 0;
    }

    public MinimumHeap(){
        items = new ArrayList<T>();
        cursor = 0;
    }

    public static void main(String[] args) {
        MinimumHeap<Integer> heap = new MinimumHeap<Integer>();
        heap.add(2);
        heap.add(3);
        heap.add(5);
        heap.add(1);
        heap.add(4);
        heap.add(7);
        heap.add(6);

        //System.out.println(heap.next());

        heap.deleteTop();
        heap.deleteTop();

        while (!heap.isEmpty()) {
            System.out.println(heap.deleteTop());
        }
    }

    private T deleteTop() {
        if(items.isEmpty()){
            return null;
        }
        //先获取顶部节点
        T maxItem = items.get(0);
        T lastItem = items.remove(items.size() - 1);
        if(items.isEmpty()){
            return lastItem;
        }
        //将尾部的节点放置顶部,下移,完成重构
        items.set(0, lastItem);
        siftDown(0);
        return maxItem;
    }

    public T first() {
        if (items.size() == 0) {
            return null;
        }
        cursor = 0;
        return items.get(0);
    }

    public T next() {
        if (cursor<0 || cursor >= items.size()){
            return null;
        }
        return items.get(cursor++);
    }

    private boolean isEmpty() {
        return items.isEmpty();
    }

    private void add(T item) {
        // 先添加到最后
        items.add(item);
        // 循环上移，以完成重构
        siftUp(items.size() - 1);
    }

    /**
     * 上移操作
     * @param index 被上移元素的起始位置。
     */
    private void siftUp(int index) {
        T intent = items.get(index);
        while (index > 0) {
            int pIndex = (index - 1) / 2;
            T parent = items.get(pIndex);
            //元素比父节点小,即上移
            if (intent.compareTo(parent) < 0){
                items.set(index,parent);
                index = pIndex;
            }else {
                break;
            }
        }
        items.set(index,intent);
    }

    private void siftDown(int index) {
        T intent = items.get(index);
        int leftIndex = 2 * index + 1;
        while(leftIndex < items.size()){
            T minChild = items.get(leftIndex);
            int minIndex = leftIndex;
            int rightIndex = leftIndex + 1;
            if (rightIndex < items.size()){
                T rightChild = items.get(rightIndex);
                if(rightChild.compareTo(minChild)<0){
                    minChild = rightChild;
                    minIndex = rightIndex;
                }
            }
            if(minChild.compareTo(intent) < 0){
                items.set(index,minChild);
                index = minIndex;
                leftIndex = 2 * index +1;
            }else{
                break;
            }
        }
        items.set(index,intent);
    }

}
