package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbOrderDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.feign.fallback.EsServiceImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "dongdongwuliu-es-service", fallback = EsServiceImplFallBack.class,url = "http://127.0.0.1:8160")
public interface EsServiceFeign {

    //下订单时将数据放入es中
    @PostMapping("/es/addEsByOrder")
    DataResult addEsByOrder(@RequestBody TbOrderDTO tbOrderDTO);

    //查询订单es数据
    @GetMapping("/es/getEsOrderInfo/{username}")
    DataResult<List<TbOrderVO>> getEsOrderInfo(@PathVariable("username")String username);

    //搜索订单根据寄件人手机号和订单号查询
    @PostMapping("/es/getEsOrderByPhoneByOrderId/{senderMobile}/{orderId}")
    DataResult<List<TbOrderVO>> getEsOrderByPhoneByOrderId(@PathVariable("senderMobile") String senderMobile,@PathVariable("orderId") String orderId);

    @PutMapping("/es/updateStatusByUsernameByOrderId")
    DataResult updateStatusByUsernameByOrderId(@RequestParam String username,@RequestParam String orderId);

    @PostMapping("/es/selectOrderEs/{phoneOrOrderId}/{orderId}/{username}")
    DataResult selectOrderEs(@PathVariable("phoneOrOrderId") String phoneOrOrderId,@PathVariable("orderId")String orderId,@PathVariable("username") String username);
}
