package org.pyx.guava.base;

import com.google.common.base.Enums;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * @author pyx
 * @date 2018/7/16
 */
public class EnumsTest {
    /**
     * 状态
     */
    enum Status {

        /**
         * 任务准备
         */
        WAIT("等待", 1),

        /**
         * 任务提交
         */
        COMMIT("提交", 2),

        /**
         * 任务成功
         */
        PASS("试题启用", 3),

        /**
         * 任务失败
         */
        FAIL("任务拒绝", 4);

        private String name;
        private int code;

        Status(String name, int ode) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    @Test
    public void testGetIfPresent(){
        Boolean bool = Enums.getIfPresent(Status.class,"WAIT").isPresent();
        Assert.assertTrue(bool);
        Boolean bool1 = Enums.getIfPresent(Status.class,"TO_WAIT").isPresent();
        Assert.assertTrue(bool1);
    }
}
