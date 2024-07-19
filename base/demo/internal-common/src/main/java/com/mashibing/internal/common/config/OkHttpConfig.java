package com.mashibing.internal.common.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import com.mashibing.internal.common.utils.X509TrustSingleton;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-29 10:17
 * To change this template use File | Settings | File and Code Templates.
 */
@Configuration
public class OkHttpConfig {

    /**
     * 配置案例
     * proxy:
     *   use: false
     *   host: proxy.example.com
     *   port: 8080
     *   timeout:
     *     connect: 10000
     *     read: 10000
     *     write: 10000
     */
    @Value("${proxy.use:false}")
    private Boolean useProxy;

    @Value("${proxy.host:proxy.example.com}")
    private String proxyHost;

    @Value("${proxy.port:8080}")
    private Integer proxyPort;

    @Value("${proxy.timeout.connect:10000}")
    private Integer connectTimeout;

    @Value("${proxy.timeout.read:10000}")
    private Integer readTimeout;

    @Value("${proxy.timeout.write:10000}")
    private Integer writeTimeout;

    private void setProxy(OkHttpClient.Builder builder) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        builder.proxy(proxy);

        // 为代理单独设置超时
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                return chain
                        .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS) // 设置代理的连接超时时间为10秒
                        .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)    // 设置代理的读取超时时间为10秒
                        .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)   // 设置代理的写入超时时间为10秒
                        .proceed(request);
            }
        });
    }

    @Bean
    @Primary
    public OkHttpClient.Builder okHttpClientBuilder() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 设置连接池的最大空闲连接数为10
        int maxIdleConnections = 10;
        // 设置空闲连接的存活时间为5分钟
        long keepAliveDuration = 3;

        /**
         * 启用连接保持策略（Connection Keep-Alive）：可以设置是否启用连接保持功能，以便在多次请求之间重用相同的连接。
         */
        builder.connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS));
        //builder.connectionPool().evictAll(); // 禁用连接池自动清理机制

        /**
         * 设置连接超时时间、读取超时时间和写入超时时间
          */
        builder.connectTimeout(10, TimeUnit.SECONDS); // 设置连接超时时间为10秒
        builder.readTimeout(10, TimeUnit.SECONDS); // 设置读取超时时间为10秒
        builder.writeTimeout(10, TimeUnit.SECONDS); // 设置写入超时时间为10秒

        /**
         * 添加拦截器 可以添加自定义的拦截器来对请求和响应进行处理，例如日志记录、添加公共请求头等。
         */
        //builder.addInterceptor(new LoggingInterceptor()); // 添加日志拦截器

        /**
         * 目前添加自定义的重试拦截器的重试策略会导致报错cannot make a new request because the previous response is still open: please call response.close()
         * 设置重试策略 可以设置重试策略，控制在出现连接问题或其他错误时是否进行重试，以及重试的次数和间隔。
         * 可以使用 Interceptor 来自定义 OkHttp 的重试策略。例如，您可以设置不同的重试时间间隔或重试条件。
         */
        builder.retryOnConnectionFailure(true); // 设置在连接失败时进行重试 默认重试一次，若需要重试N次，则要实现拦截器。
        //int maxRetries = 2; // 最大重试次数
        //long retryInterval = 1000; // 重试间隔时间（毫秒）
        //// 添加自定义的重试拦截器
        //builder.addInterceptor(new OkHttpRetryInterceptor(maxRetries, retryInterval));

        /**
         * 设置证书验证策略 可以设置证书验证策略，确保与服务器建立安全连接时使用的是正确的证书。
         */
        builder.sslSocketFactory(X509TrustSingleton.getInstance().getSSLSocketFactory(), X509TrustSingleton.getInstance());

        /**
         * 可以设置代理服务器，以便在访问互联网时使用代理。
         */
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.example.com", 8080));
        //builder.proxy(proxy);

        /**
         * HTTP/2 支持（HTTP/2 Support）：可以启用对 HTTP/2 协议的支持，以提高性能和效率
         */
        //builder.protocols(Collections.singletonList(Protocol.HTTP_2));

        if (useProxy) {
            // 设置代理
            setProxy(builder);
        }

        return builder;
    }

    @Bean
    public OkHttpClient okHttpClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        return okHttpClientBuilder().build();
    }

    @Bean
    public OkHttpClient.Builder readOkHttpClientBuilder() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 设置连接池的最大空闲连接数为128
        int maxIdleConnections = 128;
        // 设置空闲连接的存活时间为64分钟
        long keepAliveDuration = 64;

        /**
         * 启用连接保持策略（Connection Keep-Alive）：可以设置是否启用连接保持功能，以便在多次请求之间重用相同的连接。
         */
        builder.connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES));
        //builder.connectionPool().evictAll(); // 禁用连接池自动清理机制

        /**
         * 设置连接超时时间、读取超时时间4和写入超时时间
         */
        builder.connectTimeout(5, TimeUnit.SECONDS); // 设置连接超时时间为10秒
        builder.readTimeout(5, TimeUnit.SECONDS); // 设置读取超时时间为10秒
        builder.writeTimeout(5, TimeUnit.SECONDS); // 设置写入超时时间为10秒

        /**
         * 目前添加自定义的重试拦截器的重试策略会导致报错cannot make a new request because the previous response is still open: please call
         * response.close()
         * 设置重试策略 可以设置重试策略，控制在出现连接问题或其他错误时是否进行重试，以及重试的次数和间隔。
         * 可以使用 Interceptor 来自定义 OkHttp 的重试策略。例如，您可以设置不同的重试时间间隔或重试条件。
         */
        builder.retryOnConnectionFailure(true); // 设置在连接失败时进行重试 默认重试一次，若需要重试N次，则要实现拦截器。
        //int maxRetries = 2; // 最大重试次数
        //long retryInterval = 1000; // 重试间隔时间（毫秒）
        //// 添加自定义的重试拦截器
        //builder.addInterceptor(new OkHttpRetryInterceptor(maxRetries, retryInterval));

        /**
         * 设置证书验证策略 可以设置证书验证策略，确保与服务器建立安全连接时使用的是正确的证书。
         */
        builder.sslSocketFactory(X509TrustSingleton.getInstance().getSSLSocketFactory(), X509TrustSingleton.getInstance());

        if (useProxy) {
            // 设置代理
            setProxy(builder);
        }

        return builder;
    }

    @Bean
    public OkHttpClient readOkHttpClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        return readOkHttpClientBuilder().build();
    }

    @Bean
    public OkHttpClient.Builder writeOkHttpClientBuilder() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 设置连接池的最大空闲连接数为128
        int maxIdleConnections = 128;
        // 设置空闲连接的存活时间为64分钟
        long keepAliveDuration = 64;

        /**
         * 启用连接保持策略（Connection Keep-Alive）：可以设置是否启用连接保持功能，以便在多次请求之间重用相同的连接。
         */
        builder.connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES));
        //builder.connectionPool().evictAll(); // 禁用连接池自动清理机制

        /**
         * 设置连接超时时间、读取超时时间4和写入超时时间
         */
        builder.connectTimeout(5, TimeUnit.SECONDS); // 设置连接超时时间为10秒
        builder.readTimeout(5, TimeUnit.SECONDS); // 设置读取超时时间为10秒
        builder.writeTimeout(5, TimeUnit.SECONDS); // 设置写入超时时间为10秒

        /**
         * 设置证书验证策略 可以设置证书验证策略，确保与服务器建立安全连接时使用的是正确的证书。
         */
        builder.sslSocketFactory(X509TrustSingleton.getInstance().getSSLSocketFactory(), X509TrustSingleton.getInstance());

        return builder;
    }

    @Bean
    public OkHttpClient writeOkHttpClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        return writeOkHttpClientBuilder().build();
    }
}
