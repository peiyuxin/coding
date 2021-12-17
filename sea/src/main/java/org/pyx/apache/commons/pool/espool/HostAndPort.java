package org.pyx.apache.commons.pool.espool;

/**
 * <p>Title:HostAndPort
 * <p>Description:es连接host和port配置类
 * <p>Modified History:
 * @author pyx
 * @date 2021/12/17
 */
public class HostAndPort {
    private String host ;
    private int port ;
    private String schema;

    public HostAndPort(String host, int port, String schema) {
        this.host = host;
        this.port = port;
        this.schema = schema;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
