package com.dongdongwuliu.config;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/19 9:25
 * @Version 1.0
 **/
@Component
@RefreshScope //开启自动刷新配置
public class RequestFilter extends ZuulFilter {
    //每秒产生1000个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(5);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        System.out.println("获取令牌");
        boolean tryAcquire = RATE_LIMITER.tryAcquire();
        // 如果获取不到就直接停止
        if(!tryAcquire){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        return null;
    }
}
