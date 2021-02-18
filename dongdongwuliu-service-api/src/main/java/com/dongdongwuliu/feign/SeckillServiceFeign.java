package com.dongdongwuliu.feign;


import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.domain.vo.TbSeckillDiscountCouponVO;
import com.dongdongwuliu.domain.vo.TbUserDiscountCouponVO;
import com.dongdongwuliu.feign.fallback.SeckillServiceImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//   因为Fallback是通过Hystrix实现的， 所以需要开启Hystrix
//   class需要继承当前FeignClient的类
@Component
@FeignClient(value = "dongdongwuliu-seckill-service", fallback = SeckillServiceImplFallBack.class,url = "http://127.0.0.1:8110")
public interface SeckillServiceFeign {

    //查询优惠券
    @GetMapping("/seckillController/selectList")
    DataResult<List<TbSeckillDiscountCouponDTO>> selectList();


    //增加优惠券
    @PostMapping("/seckillController/addSeckillInfo")
    DataResult addSeckillInfo(TbSeckillDiscountCouponVO tbSeckillDiscountCouponVO);


    //删除优惠券
    @DeleteMapping("/seckillController/deleteInfoById/{id}")
    DataResult deleteInfoById(@PathVariable("id")Long id);


    //----------------------------------------------前台优惠券----------------------------------------------------


    @GetMapping("/seckillWebController/selectList1")
    DataResult selectList1();



    //根据id查询
    @GetMapping("/seckillWebController/selectSeckillById/{id}")
    DataResult<TbSeckillDiscountCouponVO> selectSeckillById(@PathVariable("id")Integer id);



    //提交订单
    @PostMapping("/seckillWebController/submitStockCount")
    DataResult submitStockCount(@RequestParam Long id, @RequestParam Long userId);

    //支付查询可用优惠券
    @PostMapping("/seckillWebController/selectSeckillByUserIdBySumPrice/{userId}/{sumPrice}")
    DataResult selectSeckillByUserIdBySumPrice(@PathVariable("userId")Long userId,@PathVariable("sumPrice")Integer sumPrice);

    //用户使用优惠券  进行扣减库存
    @PutMapping("/updateById")
    DataResult updateById(@RequestBody TbUserDiscountCouponVO tbUserDiscountCoupon);
}
