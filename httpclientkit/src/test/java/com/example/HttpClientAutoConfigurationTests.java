package com.example;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pyx.pyx.httpclient.pojo.HttpClientResult;
import org.pyx.pyx.httpclient.spring.boot.HttpClientAutoConfiguration;
import org.pyx.pyx.httpclient.spring.boot.HttpClientEndpoint;
import org.pyx.pyx.httpclient.util.PoolingHttpClientUtils;
import org.pyx.pyx.httpclient.util.SimpleHttpClientUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http client auto configuration tests
 *
 * @author linux_china
 */
@Configuration
public class HttpClientAutoConfigurationTests {
    private static ApplicationContext context;

    @BeforeClass
    public static void setUp() {
        context = new AnnotationConfigApplicationContext(
                HttpClientAutoConfigurationTests.class, HttpClientAutoConfiguration.class);
    }

    @Bean
    public MetricRegistry registry() {
        return new MetricRegistry();
    }

    @Test
    public void testHttpClient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient httpClient = context.getBean(HttpClient.class);
        HttpGet httpGet = new HttpGet("https://metrics.dropwizard.io/3.1.0/getting-started/");
        HttpResponse response = httpClient.execute(httpGet);
        EntityUtils.toString(response.getEntity());
        Thread.sleep(2000);
        HttpClientEndpoint endpoint = context.getBean(HttpClientEndpoint.class);
        System.out.println(objectMapper.writeValueAsString(endpoint));
    }

    @Test
    public void testPoolHttpClient() throws Exception {
        HttpClientResult result = SimpleHttpClientUtils.doGet("http://www.baidu.com");
        System.out.println(result);
        System.out.println();
        HttpClientResult result1 = PoolingHttpClientUtils.doGet("http://www.baidu.com");
        System.out.println(result1);
    }

    @Test
    public void testExecutor() throws Exception {
        Executor executor = context.getBean(Executor.class);
        String content = executor.execute(Request.Get("https://www.yahoo.com")).returnContent().asString();
        System.out.println(content);
    }

}
