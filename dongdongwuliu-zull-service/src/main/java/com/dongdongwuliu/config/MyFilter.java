package com.dongdongwuliu.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/19 8:44
 * @Version 1.0
 **/
@Component
public class MyFilter extends ZuulFilter {
    /**
     * 配置过滤类型，有四种不同生命周期的过滤器类型
     * 1. pre：路由之前
     * 2. routing：路由之时
     * 3. post：路由之后
     * 4. error：发送错误调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /** @Description 数字越小顺序越靠前，过滤的顺序
     * @Date 8:58 2021/1/19
     * @Param []
     * @return int
     **/
    @Override
    public int filterOrder() {
        return 0;
    }

    /** @Description 是否需要过滤：true 需要 false 不需要
     * @Date 8:58 2021/1/19
     * @Param []
     * @return boolean
     **/
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        return requestContext.sendZuulResponse();
    }

    /** @Description 过滤器的具体业务代码
     * @Date 8:59 2021/1/19
     * @Param []
     * @return java.lang.Object
     **/
    @Override
    public Object run() throws ZuulException {
        //获取当前线程请求上下文类
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取request域对象
        HttpServletRequest request = currentContext.getRequest();
        //获取参数
        String userName = request.getParameter("userName");
        //校验用户名是否存在，如果不存在，说明用户未登录，不放过
        if(userName == null || "".equals(userName)){
            //设置响应域对象
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(400);
            //获取response
            HttpServletResponse response = currentContext.getResponse();
            try {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("用户未登录，无法访问服务,请先登录");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
