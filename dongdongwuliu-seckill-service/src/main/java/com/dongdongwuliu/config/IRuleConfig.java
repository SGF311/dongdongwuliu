package com.dongdongwuliu.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Deacription TODO  负载均衡策略
 * @Author ASUS
 * @Date 2021/1/19 20:32
 * @Version 1.0
 **/
@Configuration
public class IRuleConfig {
    @Bean
    public IRule getRule(){
        return new RoundRobinRule();
    }
}
