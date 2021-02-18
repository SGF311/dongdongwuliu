package com.dongdongwuliu.log;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Deacription TODO
 * service  日志
 * @Author ASUS
 * @Date 2020/12/9 19:57
 * @Version 1.0
 **/
@Component
@Aspect// 定义切面类
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // 定义切点表达式
    @Pointcut("execution(public * com.dongdongwuliu.service.*.*(..))")
    public void aopLog(){}

    //定义前置通知
    @Before("aopLog()")
    public void doBefore(JoinPoint joinPoint){
        //参数获取
        Object[] args = joinPoint.getArgs();
        //获取调用方法
        Signature signature = joinPoint.getSignature();
        logger.info("正在进入 {} 方法 , 参数为: {} ",signature,args);
    }

    //定义返回通知
    @AfterReturning(pointcut = "aopLog()",returning = "result")
    public void doAfter(Object result) throws Throwable{
        //打印返回结果
        logger.info("方法执行成功,返回的结果为: {} ", JSONObject.toJSONString(result));
    }
}
