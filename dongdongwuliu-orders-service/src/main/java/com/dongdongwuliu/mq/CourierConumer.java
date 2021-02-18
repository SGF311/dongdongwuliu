package com.dongdongwuliu.mq;

import com.dongdongwuliu.domain.dto.TbCourierDTO;
import com.dongdongwuliu.mapper.TbCourierMapper;
import com.dongdongwuliu.pojo.TbCourier;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: 你哥
 * @Date: 2021/2/6 05:10
 * @Description:
 */
@Component
@RocketMQMessageListener(topic = "order_courier_topic",consumerGroup = "courier-consumer-group")
public class CourierConumer implements RocketMQListener<TbCourierDTO> {

    @Resource
    private TbCourierMapper tbCourierMapper;

    @Override
    public void onMessage(TbCourierDTO tbCourierDTO) {
        TbCourier tbCourier = new TbCourier();
        BeanUtils.copyProperties(tbCourierDTO,tbCourier);
        tbCourierMapper.insert(tbCourier);
    }
}
