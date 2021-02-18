package com.dongdongwuliu.mq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdongwuliu.constant.SeckillConstant;
import com.dongdongwuliu.dao.UserSeckillMapper;
import com.dongdongwuliu.pojo.TbUserDiscountCoupon;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName SeckillListenter
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/1/31 19:16
 * @Version 1.0
 **/
@Component
@RocketMQMessageListener(consumerGroup = "seckillMQ",topic = "submitStockCount")
public class SeckillListenter implements RocketMQListener<TbUserDiscountCoupon> {


    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private UserSeckillMapper userSeckillMapper;

    @Override
    public void onMessage(TbUserDiscountCoupon tbUserDiscountCoupon) {
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //将数据保存到表中
        userSeckillMapper.insert(tbUserDiscountCoupon);
        //从mq中通过id存放
        redisTemplate.boundHashOps(SeckillConstant.COUPONSLSIT).put(tbUserDiscountCoupon.getUserId(),tbUserDiscountCoupon);
    }
}

 

