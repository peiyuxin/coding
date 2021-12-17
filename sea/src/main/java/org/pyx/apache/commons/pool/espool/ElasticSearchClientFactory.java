package org.pyx.apache.commons.pool.espool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

/**
 * <p>Title:ElasticSearchClientFactory
 * <p>Description:es连接池工厂
 * <p>Modified History:
 *
 * @author pyx
 * @date 2021/12/17
 */
public class ElasticSearchClientFactory implements PooledObjectFactory<RestClient> {
    private HostAndPort hostAndPort;

    private String clusterName;

    public ElasticSearchClientFactory(String clusterName, HostAndPort hostAndPort){
        this.clusterName = clusterName;
        this.hostAndPort = hostAndPort;
    }

    @Override
    public PooledObject<RestClient> makeObject() throws Exception {
        RestClient client = RestClient.builder(new HttpHost(hostAndPort.getHost(),hostAndPort.getPort(),hostAndPort.getSchema())).build();
        return new DefaultPooledObject<>(client);
    }

    @Override
    public void destroyObject(PooledObject<RestClient> pooledObject) throws Exception {
        RestClient client = pooledObject.getObject();
        if(client!=null){
            try {
                client.close();
            }catch (Exception e){
                //ignore
            }
        }
    }

    @Override
    public boolean validateObject(PooledObject<RestClient> p) {
        return false;
    }

    @Override
    public void activateObject(PooledObject<RestClient> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<RestClient> p) throws Exception {

    }
}
