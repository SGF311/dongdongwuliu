package com.dongdongwuliu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {
    // RequestHandlerSelectors.basePackage() 为 Controller包路径，不然生成的文档扫描不到接口
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dongdongwuliu.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("地址微服务接口文档")  // 大标题
                .description("地址接口,这个地址有一些关于价格的增删改查.....")  // 描述
                .termsOfServiceUrl("http://www.ddit.com")  // 网络服务地址
                .version("1.0.0")     // 版本号
                .build();
    }
}