package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdongwuliu.domain.dto.TbCourierDTO;
import com.dongdongwuliu.domain.dto.TbOrderDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.feign.EsServiceFeign;
import com.dongdongwuliu.feign.TbOrderServiceFeign;
import com.dongdongwuliu.feign.TbPersonServiceFeign;
import com.dongdongwuliu.mapper.TbCourierMapper;
import com.dongdongwuliu.mapper.TbOrderDetailMapper;
import com.dongdongwuliu.mapper.TbOrderMapper;
import com.dongdongwuliu.pojo.TbCourier;
import com.dongdongwuliu.pojo.TbOrder;
import com.dongdongwuliu.pojo.TbOrderDetail;
import com.dongdongwuliu.service.TbOrderService;
import com.dongdongwuliu.config.IdWorker;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/1/30 14:06
 * @Description:
 */
@Service
public class TbOrderServiceImpl implements TbOrderService {

    @Resource
    private TbOrderServiceFeign tbOrderServiceFeign;

    @Resource
    private TbOrderMapper tbOrderMapper;

    @Resource
    private TbOrderDetailMapper tbOrderDetailMapper;

    @Resource
    private TbCourierMapper tbCourierMapper;

    @Resource
    private EsServiceFeign esServiceFeign;

    @Resource
    private TbPersonServiceFeign tbPersonServiceFeign;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional
    public void updateStatus(String username, String orderId) {
        TbOrder tbOrder = new TbOrder();
        tbOrder.setOrderId(Long.parseLong(orderId));
        TbOrder tbOrder1 = tbOrderMapper.selectById(tbOrder);
        tbOrder1.setStatus("3");
        tbOrderMapper.updateById(tbOrder1);
    }

    @Override
    public void fastAddOrderInfo(TbOrderDTO tbOrderDTO) {
        IdWorker idWorker2 = new IdWorker();
        long orderId = idWorker2.nextId();
        //生成订单  同时将订单信息放入es中
        TbOrder tbOrder = new TbOrder();
        TbOrderDetail tbOrderDetail = new TbOrderDetail();
        BeanUtils.copyProperties(tbOrderDTO,tbOrder);
        BeanUtils.copyProperties(tbOrderDTO,tbOrderDetail);
        tbOrder.setStatus("2");
        tbOrder.setOrderId(orderId);
        tbOrder.setCreateTime(new Date());
        tbOrderDetail.setSourceType("2");
        tbOrderDetail.setOrderId(orderId);
        tbOrderDetail.setOrderId(tbOrder.getOrderId());
        tbOrderMapper.insert(tbOrder);
        tbOrderDetailMapper.insert(tbOrderDetail);
        tbOrderDTO.setOrderId(orderId);
        //放入es中
        tbOrderDTO.setStatus("2");
        tbOrderDTO.setVisitTime(new Date());
        esServiceFeign.addEsByOrder(tbOrderDTO);
        // 判断是否有取件时间  如果有取件时间   使用rocketmq异步 生成配送员订单  放入MySQL数据库       //没有取件时间直接不生成配送员订单
        if (tbOrderDTO.getVisitTime() != null){
            TbCourierDTO tbCourierDTO = new TbCourierDTO();
            tbCourierDTO.setDistance(tbOrderDTO.getDistance());
            tbCourierDTO.setPersonId(tbOrderDTO.getPersonId());
            tbCourierDTO.setOrderId(orderId);
            tbCourierDTO.setIsDelete(0);
            tbCourierDTO.setStatus(0);
            rocketMQTemplate.asyncSend("order_courier_topic", tbCourierDTO, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("配送员信息发送成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("配送员信息发送失败; {}"+throwable.getMessage());
                }
            });
        }
    }

    @Override
    public void selectVisitTime() throws ParseException {
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        Date parse = sdf.parse(format);
        //获取当前时间 + 一小时
        //将当前时间转换成时间戳 减去一小时 获取当前减去一小时的时间
        Date date1 = new Date();
        String newDate = sdf.format(new Date(Long.parseLong(String.valueOf(date1.getTime() + 3600000))));      // 时间戳转换成时间
        Date newDate1 = sdf.parse(newDate);

        //根据当前时间和当前时间加一  取区间的值
        QueryWrapper<TbOrderDetail> queryWrapper = new QueryWrapper<TbOrderDetail>();
        queryWrapper.gt("visit_time",parse).lt("visit_time",newDate1);
        List<TbOrderDetail> list = tbOrderDetailMapper.selectVisitTimeGtTime(queryWrapper);

        for (TbOrderDetail tbOrderDetail : list) {
            //根据订单id查询配送员订单表
            TbCourier tbCourier = tbCourierMapper.selectById(tbOrderDetail.getOrderId());
            //根据配送员id查询他的手机号 提示发送短信
            Integer personId = tbCourier.getPersonId();
            //查询配送员手机号  ------ feign调用

            //发送短信
            tbOrderServiceFeign.getSms("17558836693");
        }

    }

    @Override
    public void updateOrderInfo(TbOrderDTO tbOrderDTO) {
        TbOrder tbOrder = new TbOrder();
        TbOrderDetail tbOrderDetail = new TbOrderDetail();
        BeanUtils.copyProperties(tbOrderDTO,tbOrder);
        BeanUtils.copyProperties(tbOrderDTO,tbOrderDetail);
        tbOrderDetail.setUpdateTime(new Date());
        tbOrderDetail.setOrderId(tbOrderDTO.getOrderId());
        tbOrder.setStatus("2");
        tbOrderMapper.updateById(tbOrder);
        tbOrderDetailMapper.updateById(tbOrderDetail);
    }

    @Override
    public void deleteOrder(Long orderId) {
        TbOrder order = new TbOrder();
        order.setOrderId(orderId);
        order.setIsDelete(1);
        tbOrderMapper.updateById(order);
    }

    @Override
    public void cancelOrderByOrderId(Long orderId) {
        TbOrder order = new TbOrder();
        order.setOrderId(orderId);
        order.setStatus("6");
        tbOrderMapper.updateById(order);
    }

    @Override
    public List<TbOrderVO> findOrderByOutTradeNoBySenderMobileByStatus(TbOrderVO tbOrderVO) {
        return tbOrderMapper.findOrderByOutTradeNoBySenderMobileByStatus(tbOrderVO.getOutTradeNo(),tbOrderVO.getSenderMobile(),tbOrderVO.getStatus());
    }

    @Override
    public TbOrderVO findOrderByOrderId(Long orderId) {
        QueryWrapper<TbOrderVO> queryWrapper = new QueryWrapper<TbOrderVO>();
        queryWrapper.likeRight("o.order_id",orderId);
        return tbOrderMapper.findOrderByOrderId(queryWrapper);
    }

    @Override
    @Transactional
    public void addOrderInfo(TbOrderDTO tbOrderDTO) {
        IdWorker idWorker2 = new IdWorker();
        long orderId = idWorker2.nextId();
        //将订单信息拆分 进行增加
        TbOrder tbOrder = new TbOrder();
        TbOrderDetail tbOrderDetail = new TbOrderDetail();
        BeanUtils.copyProperties(tbOrderDTO,tbOrder);
        BeanUtils.copyProperties(tbOrderDTO,tbOrderDetail);
        tbOrder.setStatus("1");
        tbOrder.setOrderId(orderId);
        tbOrder.setCreateTime(new Date());
        tbOrder.setCircuitId(100L);
        tbOrderDetail.setSourceType("2");
        tbOrderDetail.setOrderId(orderId);
        tbOrderMapper.insert(tbOrder);
        tbOrderDetailMapper.insert(tbOrderDetail);
        tbOrderDTO.setOrderId(orderId);
        //放入es中
        esServiceFeign.addEsByOrder(tbOrderDTO);
    }

    @Override
    public List<TbOrderVO> getInfo() {
        QueryWrapper<TbOrderVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        return tbOrderMapper.getInfo(queryWrapper);
    }
}
