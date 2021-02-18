package com.dongdongwuliu.service.impl;

import com.dongdongwuliu.dao.ConnectRepository;
import com.dongdongwuliu.pojo.Connect;
import com.dongdongwuliu.service.ConnectService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ConnectServiceImpl implements ConnectService {
    @Autowired
    private ConnectRepository connectRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    // 查询所有订单的物流信息
    @Override
    public List<Connect> getConnectList() {
        return connectRepository.findAll();
    }

    // 根据订单ID查询物流信息,并倒叙
    @Override
    public List<Connect> getConnectByOrderId(String orderId) {
        Query query = new Query(Criteria.where("orderId").is(orderId));
        query.with(Sort.by(Sort.Order.desc("date")));
        return mongoTemplate.find(query, Connect.class);
    }

    // 根据ID查询订单物流信息
    @Override
    public Connect getConnectById(String id) {
        return connectRepository.findById(id).get();
    }

    // 增加订单的物流信息
    @Transactional
    @Override
    public Boolean addConnect(Connect connect) {
        connect.setDate(new Date());
        Connect save = connectRepository.save(connect);
        return save != null;
    }

    // 修改订单的物流信息
    @Transactional
    @Override
    public Boolean updateConnect(Connect connect) {
        connect.setDate(new Date());
        Connect save = connectRepository.save(connect);
        return save != null;
    }

    // 根据ID删除订单物流信息
    @Transactional
    @Override
    public Boolean deleteConnectById(String id) {
        connectRepository.deleteById(id);
        return true;
    }

    // 根据订单ID删除订单物流信息
    @Transactional
    @Override
    public Boolean deleteConnectByOrderId(String orderId) {
        Query query = Query.query(Criteria.where("orderId").is(orderId));
        DeleteResult remove = mongoTemplate.remove(query, Connect.class);
        return true;
    }
}
