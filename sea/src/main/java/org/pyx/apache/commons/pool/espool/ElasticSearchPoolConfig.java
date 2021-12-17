package org.pyx.apache.commons.pool.espool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * <p>Title:ElasticSearchPoolConfig
 * <p>Description:es连接配置类
 * <p>Modified History:
 *
 * @author pyx
 * @date 2021/12/17
 */
public class ElasticSearchPoolConfig extends GenericObjectPoolConfig {

    private long connectTimeMillis;

    private String clusterName;

    public long getConnectTimeMillis() {
        return connectTimeMillis;
    }

    public void setConnectTimeMillis(long connectTimeMillis) {
        this.connectTimeMillis = connectTimeMillis;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
