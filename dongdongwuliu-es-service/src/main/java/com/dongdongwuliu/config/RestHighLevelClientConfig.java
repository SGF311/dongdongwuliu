package com.dongdongwuliu.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2020/12/20 18:02
 * @Version 1.0
 **/
@Configuration
public class RestHighLevelClientConfig {

    @Value("${restHighLevelClient.hostname}")
    private String hostname;

    @Value("${restHighLevelClient.port}")
    private Integer port;

    @Value("${restHighLevelClient.scheme}")
    private String scheme;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        // 指定ip 端口
        HttpHost httpHost = new HttpHost(hostname,port,scheme);
        // 创建client
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));
        return client;
    }
}
