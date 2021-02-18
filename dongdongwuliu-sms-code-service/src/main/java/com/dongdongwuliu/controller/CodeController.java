package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2020/12/19 11:06
 * @Version 1.0
 **/
@RestController
@RequestMapping("vs")
@Api(description = "验证码接口")
@RefreshScope //开启自动刷新配置
public class CodeController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /** @Description 根据手机获取验证码
     * @Date 12:18 2021/1/31
     * @Param [phone]
     * @return com.dongdongwuliu.data.DataResult
     **/
    @ApiOperation(value = "根据手机号发送验证码")
    @ApiImplicitParam(name = "phone", value = "phone", required = true, paramType = "path", dataTypeClass = String.class)
    @GetMapping("/code/{phone}")
    public DataResult getverificationCode(@PathVariable("phone")String phone) {
        DataResult dataResult = null;
        //判断传入phone是否为空 为空 返回错误
        if (StringUtils.isBlank(phone)) {
            dataResult = DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
            return dataResult;
        }
        //不为空 调用 消息队列 将手机号发送出去 消费者进行接收处理
        rocketMQTemplate.convertAndSend("verificationCode1", phone);
        //异步处理 不用等结果 就直接返回成功  就算  发送失败 有可能也是运营商的问题 和公司无关
        dataResult = DataResult.response(ResponseStatusEnum.SUCCESS);
        return dataResult;
    }
}
