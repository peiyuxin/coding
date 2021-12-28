package org.pyx.base.bit;

/**
 * @author pyx
 * @date 2021/12/28
 */
public class Long16Radix {

    public static void main(String[] args) {
        Long l = 100L;
        long rl = Long.reverse(l);
        byte[] bytes = longToBytes(l);
        System.out.println(bytes);
        System.out.println(Long.reverseBytes(l));
    }

    public static byte[] longToBytes(long x){
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            //低位存储
            b[i] = (byte)((x >> (i * 8)) & 0xFF);
        }
        return b;
    }

    public static long bytesToLong(byte[] b){
        long value = 0;
        for (int i = 0; i < b.length; i++) {
            value += ((long)b[i] &  0xffL) << (8 * i);
        }
        return value;
    }
}
