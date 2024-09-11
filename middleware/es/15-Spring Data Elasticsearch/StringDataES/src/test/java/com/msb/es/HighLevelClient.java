package com.msb.es;

import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;

import java.io.IOException;

/**
 * <h3>sjzt-common</h3>
 * <p></p>
 *
 * @author :
 * @date : 2020-05-11 16:29
 **/
public class HighLevelClient {
    private static RestClientBuilder restClientBuilder = ClientBuilders.getClientBulider();

    // 实例化客户端
    private static RestHighLevelClient restHighLevelClient;
    // 嗅探器实例化
    private static Sniffer sniffer;

    /**
     * 开启client，sniffer
     * @return
     */
    public RestHighLevelClient getClient() {
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        //十秒刷新并更新一次节点
        sniffer = Sniffer.builder(restHighLevelClient.getLowLevelClient())
                .setSniffAfterFailureDelayMillis(5000)
                .build();

        return restHighLevelClient;
    }

    /**
     *
     * 关闭sniffer client
     */
    public void closeRestHighLevelClient() {
        if (null != restHighLevelClient) {
            try {
                sniffer.close();
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}