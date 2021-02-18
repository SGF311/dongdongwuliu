package com.dongdongwuliu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/29 15:53
 * @Version 1.0
 **/
@SpringBootApplication  //springboot 启动类注解
@EnableDiscoveryClient  //nacos客户端
@EnableZuulProxy   //网关
public class ZuulServiceApi {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServiceApi.class, args);
    }
}
