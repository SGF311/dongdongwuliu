package com.dongdongwuliu.dao;

import com.dongdongwuliu.pojo.Connect;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConnectRepository extends MongoRepository<Connect, String> {
    // 根据订单ID查询物流信息
    List<Connect> findByOrderIdEquals(String orderId);
}
