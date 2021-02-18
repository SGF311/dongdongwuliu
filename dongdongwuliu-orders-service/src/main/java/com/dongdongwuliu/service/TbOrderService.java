package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.TbOrderDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.pojo.TbOrder;

import java.text.ParseException;
import java.util.List;


/**
 * @Auther: 你哥
 * @Date: 2021/1/30 14:05
 * @Description:
 */
public interface TbOrderService {
    List<TbOrderVO> getInfo();

    void addOrderInfo(TbOrderDTO tbOrderDTO);

    TbOrderVO findOrderByOrderId(Long orderId);

    List<TbOrderVO> findOrderByOutTradeNoBySenderMobileByStatus(TbOrderVO tbOrderVO);

    void deleteOrder(Long orderId);

    void updateOrderInfo(TbOrderDTO tbOrderDTO);

    void fastAddOrderInfo(TbOrderDTO tbOrderDTO);

    void selectVisitTime() throws ParseException;

    void cancelOrderByOrderId(Long orderId);

    void updateStatus(String username, String orderId);
}
