package com.dongdongwuliu.config;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @Description -
 *NONE：** 默认的，不显示任何日志
 *NONE：**仅记录请求方法、URL、响应状态码及执行时间；
 *HEADERS:** 除了BASIC中的信息会记录以外，还有请求和响应的头信息也会记录
 **FULL:** 除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据
 * @Date 21:11 2021/1/29
 * @Param
 * @return
 **/
@Configuration
public class FeignConfig {
    @Bean
    public Logger.Level feignLogger() {
        //日志级别
        return Logger.Level.FULL;
    }
}