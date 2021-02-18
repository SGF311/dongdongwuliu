package com.dongdongwuliu.service;

import com.dongdongwuliu.pojo.Connect;

import java.util.List;

public interface ConnectService {
    List<Connect> getConnectList();

    List<Connect> getConnectByOrderId(String orderId);

    Connect getConnectById(String id);

    Boolean addConnect(Connect connect);

    Boolean updateConnect(Connect connect);

    Boolean deleteConnectById(String id);

    Boolean deleteConnectByOrderId(String orderId);
}
