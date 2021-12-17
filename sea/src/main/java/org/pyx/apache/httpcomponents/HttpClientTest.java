package org.pyx.apache.httpcomponents;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author pyx
 * @date 2021/12/13
 */
public class HttpClientTest {

    private PoolingHttpClientConnectionManager pool;

    @Before
    public void init(){
        pool = new PoolingHttpClientConnectionManager(100, TimeUnit.MILLISECONDS);
    }

    public CloseableHttpClient getClient(){
        return HttpClientBuilder.create().setConnectionManager(pool).build();
    }

    @Test
    public void test() throws IOException {
        CloseableHttpResponse response = getClient().execute(new HttpGet("http://www.baidu.com"));
        System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
        System.out.println("/////////////////////////////");
        response = getClient().execute(new HttpGet("http://www.baidu.com"));
        System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));

    }
}
