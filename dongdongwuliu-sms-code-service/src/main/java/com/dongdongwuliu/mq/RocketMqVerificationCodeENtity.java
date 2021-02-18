package com.dongdongwuliu.mq;

import com.aliyuncs.exceptions.ClientException;
import com.dongdongwuliu.constant.VerificationCodeContstant;
import com.dongdongwuliu.util.SmsUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2020/12/19 13:10
 * @Version 1.0
 **/
@Service
@RocketMQMessageListener(topic = "verificationCode1",consumerGroup = "dongdongwuliu_code_consume")
public class RocketMqVerificationCodeENtity implements RocketMQListener<String> {
    private Logger logger = LoggerFactory.getLogger(RocketMqVerificationCodeENtity.class);
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private SmsUtil smsUtil;
    //模板编号
    @Value("${aliyun.sms.template_code}")
    private String template_code;
    //短信签名
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;
    @Override
    public void onMessage(String phone) {
        //随机生成4位数验证码
        String code = RandomStringUtils.randomNumeric(4);
        //设置redis的key
        String key = VerificationCodeContstant.VERIFICATION_CODE + phone;
        //获取字符串操作对象
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key,code);
        //设置key有效期 为 5 分钟  验证码五分钟后生效
        redisTemplate.expire(key,5, TimeUnit.MINUTES);
        try {
            smsUtil.sendSms(phone,template_code,sign_name,"{\"code\":\""+code+"\"}");
        } catch (ClientException e) {
            logger.error("方法执行失败", e);
        }
    }
}
