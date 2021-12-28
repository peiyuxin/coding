package org.pyx.base.bit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 我有一个字节[4]，它包含一个32位的无符号整数(在大端顺序)，我需要将它转换为long(因为int不能保存一个无符号数)。
 另外，我如何做反之亦然(即从long包含一个32位无符号整数字节[4])?
 * @author pyx
 * @date 2021/12/27
 */
public class EndianTest {

    public static void main(String[] args) {
        byte[] payload = toArray(-1991249);
        int number = fromArray(payload);
        System.out.println(number);

        System.out.println("按照字节序大端序来从byte[4]中解出int");
        int result = 0xFF & payload[0];
        System.out.println(result);
        result <<= 8;
        result += 0xFF & payload[1];
        result <<= 8;
        result += 0xFF & payload[2];
        result <<= 8;
        result += 0xFF & payload[3];
        System.out.println(result);
    }

    public static int fromArray(byte[] payload){
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getInt();
    }

    private static byte[] toArray(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(value);
        buffer.flip();
        return buffer.array();
    }
}
