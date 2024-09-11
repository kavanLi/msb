package com.msb.es.util;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;

import java.io.IOException;

public class ESClient {
    private static ESClient ESClient;
    private String host = "localhost:9200,localhost:9201";
    private RestClientBuilder builder;
    private static Sniffer sniffer;
    private static RestHighLevelClient highClient;

    private ESClient(){
    }

    public static ESClient getInstance() {
        if (ESClient == null) {
            synchronized (ESClient.class) {
                if (ESClient == null) {
                    ESClient = new ESClient();
                    ESClient.getRestClientBuilder();
                }
            }
        }
        return ESClient;
    }

    public RestClientBuilder getRestClientBuilder() {
        String[] hosts = host.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String[] host = hosts[i].split(":");
            httpHosts[i] = new HttpHost(host[0], Integer.parseInt(host[1]), "http");
        }

        builder = RestClient.builder(httpHosts);

        //region 在Builder中设置请求头
        //  1.设置请求头
        Header[] defaultHeaders = new Header[]{
                new BasicHeader("Content-Type", "application/json")
        };
        builder.setDefaultHeaders(defaultHeaders);
        //endregion
//        RestClient restClient = builder.build();

        //启用嗅探器
//        SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
//        builder.setFailureListener(sniffOnFailureListener);
//        sniffer = Sniffer.builder(restClient)
//                .setSniffIntervalMillis(5000)
//                .setSniffAfterFailureDelayMillis(10000)
//                .build();
//        sniffOnFailureListener.setSniffer(sniffer);
        return builder;
    }

    public RestHighLevelClient getHighLevelClient() {
        if (highClient == null) {
            synchronized (ESClient.class) {
                if (highClient == null) {
                    highClient = new RestHighLevelClient(builder);
//                    //十秒刷新并更新一次节点
                    sniffer = Sniffer.builder(highClient.getLowLevelClient())
                            .setSniffIntervalMillis(5000)
                            .setSniffAfterFailureDelayMillis(15000)
                            .build();
                }
            }
        }
        return highClient;
    }
    /**
     *
     * 关闭sniffer client
     */
    public void closeClient() {
        if (null != highClient) {
            try {
                sniffer.close();    //需要在highClient close之前操作
                highClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
