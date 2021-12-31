package org.pyx.apache.httpcomponents;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pyx
 * @date 2021/12/31
 */
public class HttpConnectionPoolUtil {

    private static Logger LOG = LoggerFactory.getLogger(HttpConnectionPoolUtil.class);

    private static final int CONNECT_TIMEOUT = Config.getHttpConnectTimeout();// 设置连接建立的超时时间为10s
    private static final int SOCKET_TIMEOUT = Config.getHttpSocketTimeout();
    private static final int MAX_CONN = Config.getHttpMaxPoolSize(); // 最大连接数
    private static final int Max_PRE_ROUTE = Config.getHttpMaxPoolSize();
    private static final int MAX_ROUTE = Config.getHttpMaxPoolSize();
    private static CloseableHttpClient CLIENT; // 发送请求的客户端单例
    private static PoolingHttpClientConnectionManager MANAGER; //连接池管理类
    private static ScheduledExecutorService monitorExecutor;

    private final static Object syncLock = new Object(); // 相当于线程锁,用于线程安全

    static{
        LayeredConnectionSocketFactory ssl = null;
        try {
            ssl = SSLConnectionSocketFactory.getSystemSocketFactory();
        } catch (final SSLInitializationException ex) {
            final SSLContext sslcontext;
            try {
                sslcontext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
                sslcontext.init(null, null, null);
                ssl = new SSLConnectionSocketFactory(sslcontext);
            } catch (final SecurityException ignore) {
            } catch (final KeyManagementException ignore) {
            } catch (final NoSuchAlgorithmException ignore) {
            }
        }
        final Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", ssl != null ? ssl : SSLConnectionSocketFactory.getSocketFactory())
            .build();

        MANAGER = new PoolingHttpClientConnectionManager(sfr);
        MANAGER.setMaxTotal(MAX_CONN);//最大连接数
        MANAGER.setDefaultMaxPerRoute(Max_PRE_ROUTE);//路由最大连接数
        MANAGER.setValidateAfterInactivity(1000);
    }

    /**
     * 对http请求进行基本设置
     *
     * @param httpRequestBase http请求
     */
    private static void setRequestConfig(HttpRequestBase httpRequestBase) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT).build();
        httpRequestBase.setConfig(requestConfig);
    }

    public static CloseableHttpClient getHttpClient(String url) {
        String hostName = url.split("/")[2];
        //System.out.println(hostName);
        int port = 80;
        if (hostName.contains(":")) {
            String[] args = hostName.split(":");
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        }
        if (CLIENT == null) {
            //多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
            synchronized (syncLock) {
                CLIENT = createHttpClient(hostName, port);
                //开启监控线程,对异常和空闲线程进行关闭
                monitorExecutor = Executors.newScheduledThreadPool(1);
                monitorExecutor.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        //关闭异常链接
                        MANAGER.closeExpiredConnections();
                        //关闭5s空闲的连接
                        MANAGER.closeIdleConnections(Config.getHttpIdelTimeout(), TimeUnit.MILLISECONDS);
                        LOG.info("close expired and idle for over 5s connection");
                    }
                },Config.getHttpMonitorInterval(),Config.getHttpMonitorInterval(),TimeUnit.MILLISECONDS);
            }
        }
        return CLIENT;
    }

    /**
     * 根据host和port构建httpclient实例
     *
     * @param hostName 要访问的域名
     * @param port     要访问的端口
     * @return
     */
    private static CloseableHttpClient createHttpClient(String hostName, int port) {


        //请求失败时,进行请求重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException e, int executionCount, HttpContext httpContext) {
                if (executionCount > 3) {
                    //重试超过3次,放弃请求
                    LOG.error("retry has more than 3 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException) {
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    LOG.error("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException) {
                    // SSL握手异常
                    LOG.error("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException) {
                    //超时
                    LOG.error("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException) {
                    // 服务器不可达
                    LOG.error("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException) {
                    // 连接超时
                    LOG.error("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException) {
                    LOG.error("SSLException");
                    return false;
                }

                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    //如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            }
        };
        CloseableHttpClient client = HttpClientBuilder.create().setConnectionManager(MANAGER)
            .setRetryHandler(handler).build();
        return client;
    }
}
