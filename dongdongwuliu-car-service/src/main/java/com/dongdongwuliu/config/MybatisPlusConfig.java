package com.dongdongwuliu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/1/10 16:55
 * @Version 1.0
 **/
@Configuration //标志配置类
public class MybatisPlusConfig {

    @Bean  //注入Spring到容器中  paginationInterceptor:分页插件类
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}