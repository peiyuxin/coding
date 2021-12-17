package org.pyx.apache.commons.pool.espool;

/**
 * @author pyx
 * @date 2021/12/17
 */

import org.elasticsearch.client.RestClient;

/**
 * <p>Title:ElasticSearchPool
 * <p>Description:es连接池
 * <p>Modified History:
 *
 * @author Administrator
 * @date 2018/10/25 0025 9:19
 */
public class ElasticSearchPool extends Pool<RestClient> {

    private String clusterName;
    private HostAndPort hostAndPort;

    public ElasticSearchPool(ElasticSearchPoolConfig config,HostAndPort hostAndPort){
        super(config, new ElasticSearchClientFactory(config.getClusterName(), hostAndPort));
        this.clusterName = config.getClusterName();
        this.hostAndPort = hostAndPort;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }
}
