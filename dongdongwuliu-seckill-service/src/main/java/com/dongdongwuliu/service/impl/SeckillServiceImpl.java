package com.dongdongwuliu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdongwuliu.constant.SeckillConstant;
import com.dongdongwuliu.dao.SeckillMapper;
import com.dongdongwuliu.dao.UserSeckillMapper;
import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.domain.vo.TbUserDiscountCouponVO;
import com.dongdongwuliu.pojo.TbSeckillDiscountCoupon;
import com.dongdongwuliu.pojo.TbUserDiscountCoupon;
import com.dongdongwuliu.service.SeckillService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SeckillServiceImpl
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/1/30 11:11
 * @Version 1.0
 **/
@Service
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private SeckillMapper seckillMapper;

    @Resource
    private UserSeckillMapper userSeckillMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    //查询全部
    @Override
    public List<TbSeckillDiscountCouponDTO> selectList() {
        List<TbSeckillDiscountCoupon> list = seckillMapper.selectList(null);
        List<TbSeckillDiscountCouponDTO> discountCouponDTOS = new ArrayList<>();
        for (TbSeckillDiscountCoupon tbSeckillDiscountCoupon : list) {
            TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO = new TbSeckillDiscountCouponDTO();
            BeanUtils.copyProperties(tbSeckillDiscountCoupon,tbSeckillDiscountCouponDTO);
            discountCouponDTOS.add(tbSeckillDiscountCouponDTO);
        }
        return discountCouponDTOS;
    }

    //增加
    @Override
    public Integer addSeckillInfo(TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO) {
        TbSeckillDiscountCoupon tbSeckillDiscountCoupon = new TbSeckillDiscountCoupon();
        BeanUtils.copyProperties(tbSeckillDiscountCouponDTO,tbSeckillDiscountCoupon);
        tbSeckillDiscountCoupon.setCreateTime(new Date());
        int insert = seckillMapper.insert(tbSeckillDiscountCoupon);
        return insert;
    }

    //回显
    @Override
    public TbSeckillDiscountCouponDTO selectSeckillById(Integer id) {
        TbSeckillDiscountCoupon tbSeckillDiscountCoupon = seckillMapper.selectById(id);
        TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO = new TbSeckillDiscountCouponDTO();
        BeanUtils.copyProperties(tbSeckillDiscountCoupon,tbSeckillDiscountCouponDTO);
        return tbSeckillDiscountCouponDTO;
    }


    //修改
    @Override
    public Integer updateSeckillByInfo(TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO) {
        TbSeckillDiscountCoupon seckillDiscountCoupon = new TbSeckillDiscountCoupon();
        BeanUtils.copyProperties(seckillDiscountCoupon,tbSeckillDiscountCouponDTO);
        Integer i = seckillMapper.updateById(seckillDiscountCoupon);
        return i;
    }

    //删除
    @Override
    public Integer deleteInfoById(Long id) {

        return seckillMapper.deleteById(id);
    }


    //查询全部
    @Override
    public List<TbSeckillDiscountCouponDTO> selectList1() {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        //查询redis
        List<TbSeckillDiscountCoupon> seckillDiscountCoupons = (List<TbSeckillDiscountCoupon>) redisTemplate.boundValueOps(SeckillConstant.COUPONSLSIT).get();
        List<TbSeckillDiscountCouponDTO> discountCouponDTOS = null;
        //查看秒杀开始结束时间
        if (null == seckillDiscountCoupons || seckillDiscountCoupons.size() <= 0){
            //第一种方式
            QueryWrapper<TbSeckillDiscountCoupon> queryWrapper = new QueryWrapper<>();
            //秒杀开始时间小于等于当前时间,结束时间大于当前时间
            queryWrapper.lt("start_time",new Date()).gt("end_time",new Date());
            List<TbSeckillDiscountCoupon> list = seckillMapper.selectList(queryWrapper);
            discountCouponDTOS = new ArrayList<>();
            //转换成dto
            for (TbSeckillDiscountCoupon tbSeckillDiscountCoupon : list) {
                //缓存预热
                String string = JSONObject.toJSONString(tbSeckillDiscountCoupon);
                //将剩余库存放入缓存中
                redisTemplate.boundHashOps(SeckillConstant.STOCKCOUNT).put(tbSeckillDiscountCoupon.getId()+"",string);
                //抢购预减库存
                redisTemplate.boundValueOps(tbSeckillDiscountCoupon.getId()+"").set(tbSeckillDiscountCoupon.getStockCount() + "");

                //定义dto对象
                TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO = new TbSeckillDiscountCouponDTO();
                //pojo转换成dto进行传输
                BeanUtils.copyProperties(tbSeckillDiscountCoupon,tbSeckillDiscountCouponDTO);
                discountCouponDTOS.add(tbSeckillDiscountCouponDTO);
            }
        }
        String string = JSONObject.toJSONString(discountCouponDTOS);
        //放入缓存中
        redisTemplate.boundValueOps(SeckillConstant.COUPONSLSIT).set(string);
        return discountCouponDTOS;
    }



    //秒杀抢优惠券
    @Override
    public void submitStockCount(Long id, Long userId) {

        //设置序列话
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        //从redis中查询出要秒杀的优惠券
        
        String str = (String) redisTemplate.boundHashOps(SeckillConstant.STOCKCOUNT).get(id + "");
        TbSeckillDiscountCoupon tbSeckillDiscountCoupon = JSONObject.parseObject(str, TbSeckillDiscountCoupon.class);
        //库存剩余

        Integer stockCount=Integer.parseInt(redisTemplate.boundValueOps(id+"").get()+"");
        if (null == tbSeckillDiscountCoupon){
            //通过抛异常的方式通知controller信息 相当return
            throw new RuntimeException("优惠券不存在");
        }
        if (stockCount.intValue() <=0){
            throw new RuntimeException("优惠券已抢完");
        }

        //扣减库存
        Long decrement = redisTemplate.boundValueOps(id).decrement();
        tbSeckillDiscountCoupon.setStockCount(decrement.intValue());


        String string = JSONObject.toJSONString(tbSeckillDiscountCoupon);
        //减完的库存存放到redis中
        redisTemplate.boundValueOps(SeckillConstant.COUPONSLSIT).set(string);

        //如果优惠券已抢罄，同步数据库删除redis
        if (decrement.intValue() <=0){
            seckillMapper.insert(tbSeckillDiscountCoupon);
           // 同步数据库删除redis
            redisTemplate.delete(SeckillConstant.COUPONSLSIT);
        }
        //入库
        TbUserDiscountCoupon tbUserDiscountCoupon = new TbUserDiscountCoupon();
        tbUserDiscountCoupon.setId(id);
        tbUserDiscountCoupon.setTitle(tbSeckillDiscountCoupon.getTitle());
        tbUserDiscountCoupon.setSmallPic(tbSeckillDiscountCoupon.getSmallPic());
        tbUserDiscountCoupon.setPrice(tbSeckillDiscountCoupon.getPrice());
        tbUserDiscountCoupon.setCreateTime(new Date());
        tbUserDiscountCoupon.setSeckillStartTime(tbSeckillDiscountCoupon.getSeckillStartTime());
        tbUserDiscountCoupon.setSeckillEndTime(tbSeckillDiscountCoupon.getSeckillEndTime());
        tbUserDiscountCoupon.setType(tbSeckillDiscountCoupon.getType());
        tbUserDiscountCoupon.setSumPrice(tbSeckillDiscountCoupon.getSumPrice());
        tbUserDiscountCoupon.setLosePrice(tbSeckillDiscountCoupon.getLosePrice());
        tbUserDiscountCoupon.setIntroduction(tbSeckillDiscountCoupon.getIntroduction());
        //用户id
        tbUserDiscountCoupon.setUserId(userId);
        tbUserDiscountCoupon.setIsDelete(0);

        //发送mq
        rocketMQTemplate.convertAndSend("submitStockCount",tbUserDiscountCoupon);


    }

    @Override
    public List<TbUserDiscountCoupon> selectSeckillByUserIdBySumPrice(Long userId, Integer sumPrice) {
        QueryWrapper<TbUserDiscountCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).ge("sum_price",sumPrice).eq("is_delete",0);
        List<TbUserDiscountCoupon> seckillDiscountCoupons = userSeckillMapper.selectList(queryWrapper);
        return seckillDiscountCoupons;
    }

    @Override
    public void updateById(TbUserDiscountCouponVO tbUserDiscountCoupon) {
//        redisTemplate.delete(tbUserDiscountCoupon.getUserId());
        TbUserDiscountCoupon tbUserDiscountCoupon1 = new TbUserDiscountCoupon();
        BeanUtils.copyProperties(tbUserDiscountCoupon,tbUserDiscountCoupon1);
        tbUserDiscountCoupon1.setIsDelete(2);
        userSeckillMapper.updateById(tbUserDiscountCoupon1);
    }
}

 

