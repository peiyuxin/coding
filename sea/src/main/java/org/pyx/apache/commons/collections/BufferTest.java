package org.pyx.apache.commons.collections;


import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.junit.Test;

/**
 * @author pyx
 * @date 2021/12/1
 */
public class BufferTest {
    @Test
    public void testUnboundedFifoBuffer() {
        Buffer buffer = new UnboundedFifoBuffer();
        buffer.add("A");
        buffer.add("B");
        buffer.add("C");
        buffer.add("D");
        buffer.add("E");
        System.out.println(buffer.get());
        System.out.println(buffer.remove());
        System.out.println(buffer.remove());
        System.out.println(buffer);
    }
}
