package org.pyx.apache.commons.pool.espool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <p>Title:Pool
 * <p>Description:es连接对象池
 * <p>Modified History:
 *
 * @author pyx
 * @date 2021/12/13
 */
public class Pool<T> implements Cloneable{

    private final static Logger LOG = LoggerFactory.getLogger(Pool.class);

    protected GenericObjectPool<T> internalPool ;

    public Pool(){
        super();
    }

    public Pool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory){
        initPool(poolConfig, factory);
    }

    public void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {

        if (this.internalPool != null) {
            try {
                closeInternalPool();
            } catch (Exception e) {
                //do nothing
            }
        }

        this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
    }

    protected void closeInternalPool(){
        try {
            internalPool.close();
        } catch (Exception e) {
            LOG.info("Could not destroy the pool", e);
        }
    }

    public T getResource(){
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            LOG.info("Could not get a resource from the pool", e);
        }
        return null;
    }

    /**
     * 副产品不优雅
     * @param resource
     */
    public void returnResource(final T resource){
        if (resource != null) {
            returnResourceObject(resource);
        }
    }

    private void returnResourceObject(final T resource){
        if (resource == null) {
            return;
        }
        try {
            internalPool.returnObject(resource);
        } catch (Exception e) {
            LOG.info("Could not return the resource to the pool", e);
        }
    }
}
