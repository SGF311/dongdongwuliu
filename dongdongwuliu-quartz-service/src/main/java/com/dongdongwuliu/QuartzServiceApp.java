package com.dongdongwuliu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/29 15:53
 * @Version 1.0
 **/
@SpringBootApplication
@EnableScheduling
@EnableSwagger2 //Swagger 文档
@EnableDiscoveryClient //注册与发现 注册中心
@EnableFeignClients  //需要声明式feign的客户端
@EnableCircuitBreaker //熔断
public class QuartzServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(QuartzServiceApp.class,args);
    }
}
