package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.vo.ConnectVO;
import com.dongdongwuliu.feign.fallback.ConnectServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "dongdongwuliu-connect-service", fallback = ConnectServiceFeignFallback.class,
        url = "http://127.0.0.1:8090")
public interface ConnectServiceFeign {

    // 查询所有订单的物流信息
    @GetMapping("/connect/getConnectList")
    DataResult<List<ConnectVO>> getConnectList();

    // 根据订单ID查询物流信息,并倒叙
    @GetMapping("/connect/getConnectByOrderId/{orderId}")
    DataResult<List<ConnectVO>> getConnectByOrderId(@PathVariable("orderId")String orderId);

    // 根据ID查询订单物流信息
    @GetMapping("/connect/{id}")
    DataResult<ConnectVO> getConnectById(@PathVariable("id")String id);

    // 增加订单的物流信息
    @PutMapping("/connect")
    DataResult addConnect(@RequestBody ConnectVO connectVO);

    // 修改订单的物流信息
    @PutMapping("/connect/updateConnect")
    DataResult updateConnect(@RequestBody ConnectVO connectVO);

    // 根据ID删除订单物流信息
    @DeleteMapping("/connect/{id}")
    DataResult deleteConnectById(@PathVariable("id")String id);

    // 根据订单ID删除订单物流信息
    @DeleteMapping("/connect/deleteConnectByOrderId/{orderId}")
    DataResult deleteConnectByOrderId(@PathVariable("orderId")String orderId);
}
