package org.pyx.pyx.httpclient.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.pyx.pyx.httpclient.pojo.HttpClientResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pyx
 * @date 2019/2/13
 */
public class PoolingHttpClientUtils<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoolingHttpClientUtils.class);
    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;

    static PoolingHttpClientConnectionManager connectionManager;
    static {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(500);
        connectionManager.setDefaultMaxPerRoute(50);//例如默认每路由最高50并发，具体依据业务来定

        //todo 定期删除idle的链接
    }


    public static final ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
        @Override
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            HeaderElementIterator it = new BasicHeaderElementIterator
                (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase
                    ("timeout")) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return 60 * 1000;//如果没有约定，则默认定义时长为60s
        }
    };

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url 请求地址
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param headers 请求头集合
     * @param params 请求参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws
        Exception {
        // 创建httpClient对象
        //CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .setKeepAliveStrategy(myStrategy)
            .setDefaultRequestConfig(RequestConfig.custom().setStaleConnectionCheckEnabled(true).build())
            .build();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpGet);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * Description: 封装请求头
     * @param params
     * @param httpMethod
     */
    public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
        throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }

    /**
     * Description: 获得响应结果
     *
     * @param httpResponse
     * @param httpClient
     * @param httpMethod
     * @return
     * @throws Exception
     */
    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
                                                       CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws
        Exception {
        // 执行请求
        httpResponse = httpClient.execute(httpMethod);

        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            String content = "";
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
            return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * Description: 释放资源
     *
     * @param httpResponse
     * @param httpClient
     * @throws IOException
     */
    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }


    /*
    private static final ResponseHandler<CustomResponse> responseHandler = new ResponseHandler<CustomResponse>() {
        @Override
        public CustomResponse handleResponse(HttpResponse response)
            throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(
                    statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content!");
            }
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            Reader reader = new InputStreamReader(entity.getContent(), charset);
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.fromJson(reader, CustomResponse.class);
        }
    };*/

}
