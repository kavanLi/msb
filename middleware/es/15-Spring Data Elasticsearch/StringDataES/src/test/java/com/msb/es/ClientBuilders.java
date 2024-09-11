package com.msb.es;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.*;

/**
 * <h3>sjzt-common</h3>
 * <p></p>
 *
 * @author :
 * @date : 2020-05-11 16:23
 **/
public class ClientBuilders {
    private static final String CLUSTER_HOSTNAME_PORT = "localhost:9201,localhost:9202";
    public static void main(String[] args) throws InterruptedException {
        getClientBulider();
//        RestHighLevelClient restHighLevelClient = new HighLevelClient().getClient();
//        while (true) {
//            Thread.sleep(5000);
//            System.out.println(restHighLevelClient);
//        }
    }

    /**
     * 初始化 clientBuilder的详细说明
     *
     * @return
     */
    public static RestClientBuilder getClientBulider() {

        String[] hosts = CLUSTER_HOSTNAME_PORT.split(",");

        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String[] host = hosts[i].split(":");
            httpHosts[i] = new HttpHost(host[0], Integer.parseInt(host[1]), "http");
        }
        RestClientBuilder builder = RestClient.builder(httpHosts);


        //region 在构建 RestClient 实例时可以设置以下的可选配置参数

        //region 设置请求头，避免每个请求都必须指定
        Header[] defaultHeaders = new Header[]{
                new BasicHeader("header", "application/json")
        };
        builder.setDefaultHeaders(defaultHeaders);
        //endregion

        //region 设置每次节点发生故障时收到通知的侦听器。内部嗅探到故障时被启用
        builder.setFailureListener(new RestClient.FailureListener() {
            public void onFailure(Node node) {
                super.onFailure(node);
            }
        });
        //endregion

        //region 设置修改默认请求配置的回调（例如：请求超时，认证，或者其他设置）
        builder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setSocketTimeout(10000));
        //endregion

        //region 设置修改 http 客户端配置的回调（例如：ssl 加密通讯，线程IO的配置，或其他任何         设置）
        /*5.//设置修改 http 客户端配置的回调（例如：ssl 加密通讯，线程IO的配置，或其他任何         设置）*/
//        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(
//                    HttpAsyncClientBuilder httpClientBuilder) {
//                return httpClientBuilder.setProxy(
//                        new HttpHost("proxy", 9000, "http"));
//            }
//        });
        //endregion

        //region 跳过向 master only 节点发送请求
        /*6. 跳过向 master only 节点发送请求*/
        builder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        //endregion

        //region 简单的身份认证
        // 简单的身份认证
//        final CredentialsProvider credentialsProvider =
//                new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "elaser"));
//
//
//        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
//                httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                //线程设置
//                httpAsyncClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(10).build());
//                return httpAsyncClientBuilder;
//            }
//        });
        //endregion

        //endregion

        return builder;
    }
}