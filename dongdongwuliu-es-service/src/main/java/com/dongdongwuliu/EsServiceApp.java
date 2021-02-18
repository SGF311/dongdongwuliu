package com.dongdongwuliu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Auther: 你哥
 * @Date: 2021/2/3 15:29
 * @Description:
 */
@EnableSwagger2 //Swagger 文档
@SpringBootApplication
@EnableDiscoveryClient //注册与发现 注册中心
@EnableFeignClients  //需要声明式feign的客户端
@EnableCircuitBreaker //熔断
public class EsServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(EsServiceApp.class,args);
    }
}
