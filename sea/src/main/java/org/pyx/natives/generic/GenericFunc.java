package org.pyx.natives.generic;

/**
 * 泛型方法
 * @author pyx
 * @date 2018/7/19
 */
public class GenericFunc {
    public static <K,V> boolean compare(Pair<K,V> p1,Pair<K,V> p2){
        return p1.getKey().equals(p2.getKey()) &&
            p1.getValue().equals(p2.getValue());
    }

    /**
     * 边界符
     * 实现这样一个功能，查找一个泛型数组中大于某个特定元素的个数
     * @return
     */
    public static <T extends Comparable> int countGreaterThan(T[] anArray, T elem) {
        int count = 0;
        for (T e : anArray) {
            //if (e > elem) {  // compiler error
            if (e.compareTo(elem) > 0){
                ++count;
            }
        }
        return count;
    }



    public static void main(String[] args) {
        Pair<Integer, String> p1 = new Pair<>(1, "apple");
        Pair<Integer, String> p2 = new Pair<>(2, "orange");
        boolean same = GenericFunc.<Integer,String>compare(p1,p2);
        same = compare(p1,p2);
        System.out.println(same);
    }
}
class Pair<K, V> {
    private K key;
    private V value;
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    public void setKey(K key) { this.key = key; }
    public void setValue(V value) { this.value = value; }
    public K getKey()   { return key; }
    public V getValue() { return value; }
}