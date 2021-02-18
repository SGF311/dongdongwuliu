package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.feign.fallback.TbOrderServiceImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "dongdongwuliu-orders-service", fallback = TbOrderServiceImplFallBack.class,url = "http://127.0.0.1:8099")
public interface TbOrderServiceFeign {

    //调用短信服务
    @PostMapping("/vs/code/{phone}")
    DataResult getSms(@PathVariable("phone") String phone);

    @GetMapping("/order/getInfo")
    DataResult getInfo();

    @PostMapping("/order/findOrderByOutTradeNoBySenderMobileByStatus")
    DataResult findOrderByOutTradeNoBySenderMobileByStatus(@RequestBody TbOrderVO tbOrderVO);

    @GetMapping("/cargotype/getCargoTypeInfo")
    DataResult getCargoTypeInfo();

    @PostMapping("/order/addOrderInfo")
    DataResult addOrderInfo(@RequestBody TbOrderVO tbOrderVO);

    @GetMapping("/order/selectVisitTime")
    DataResult selectVisitTime();

    @PostMapping("/order/findOrderByOrderId/{orderId}")
    DataResult findOrderByOrderId(@PathVariable("orderId") String orderId);

    @PutMapping("/order/updateOrderInfo")
    DataResult updateOrderInfo(@RequestBody TbOrderVO tbOrderVO);

    @DeleteMapping("/order/deleteOrder/{orderId}")
    DataResult deleteOrder(@PathVariable("orderId") Long orderId);

    @DeleteMapping("/order/cancelOrderByOrderId/{orderId}")
    DataResult cancelOrderByOrderId(@PathVariable("orderId") Long orderId);

    @PostMapping("/order/fastAddOrderInfo")
    DataResult fastAddOrderInfo(@RequestBody TbOrderVO tbOrderVO);

    //修改订单状态
    @PutMapping("/order/updateStatus")
    DataResult updateStatus(@RequestParam String username,@RequestParam String orderId);
}
