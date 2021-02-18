package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.TbOrderDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface EsService {
    void addEsByOrder(TbOrderDTO tbOrderDTO) throws IOException;

    List<TbOrderVO> getEsOrderInfo(String username) throws IOException, ParseException;

    List<TbOrderVO> getEsOrderByPhoneByOrderId(String senderMobile, String orderId) throws IOException, ParseException;

    void updateStatusByUsernameByOrderId(String username, String orderId) throws IOException;

    List<TbOrderVO> selectOrderEs(String phoneOrOrderId,String orderId,String username) throws IOException;
}
