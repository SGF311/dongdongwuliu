package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.domain.dto.TbUserDTO;
import com.dongdongwuliu.domain.vo.TbSeckillDiscountCouponVO;
import com.dongdongwuliu.domain.vo.TbUserVO;
import com.dongdongwuliu.feign.SeckillServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName TbSeckillController
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/4 19:24
 * @Version 1.0
 **/
@Controller
@RequestMapping("tbSeckillController")
@RefreshScope //开启自动刷新配置
public class TbSeckillController {

    @Autowired
    private SeckillServiceFeign seckillServiceFeign;


//    //查询前台全部优惠券
//    @GetMapping("selectList1")
//    @ResponseBody
//    public List<TbSeckillDiscountCouponVO> selectList1() {
//        DataResult<List<TbSeckillDiscountCouponDTO>> listDataResult = seckillServiceFeign.selectList1();
//        String jsonString = JSONObject.toJSONString(listDataResult.getData());
//        List<TbSeckillDiscountCouponVO> list = JSONObject.parseObject(jsonString, List.class);
//        return list;
//    }



    @RequestMapping("/submitStockCount")
    @ResponseBody
    public DataResult submitStockCount(Long id){
        TbUserDTO user = (TbUserDTO) SecurityUtils.getSubject().getPrincipal();

        DataResult dataResult = seckillServiceFeign.submitStockCount(id, user.getId());
        return dataResult;



    }
}

 

