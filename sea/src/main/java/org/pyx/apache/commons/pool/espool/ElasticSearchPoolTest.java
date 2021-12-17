package org.pyx.apache.commons.pool.espool;

import org.elasticsearch.client.RestClient;

/**
 * @author pyx
 * @date 2021/12/17
 */
public class ElasticSearchPoolTest {

    public static void main(String[] args) {
        HostAndPort hostAndPort = new HostAndPort("172.31.133.21",19200,"http");
        ElasticSearchPoolConfig config = new ElasticSearchPoolConfig();
        //超时时间
        config.setConnectTimeMillis(8000);
        //最大连接
        config.setMaxTotal(10);
        //集群名称
        config.setClusterName("pyx_es");
        ElasticSearchPool pool = new ElasticSearchPool(config,hostAndPort);

        for(int i=0;i<100;i++){
            RestClient client = (RestClient)pool.getResource();
            System.out.println(client.toString());
            pool.returnResource(client);
        }
    }
}
