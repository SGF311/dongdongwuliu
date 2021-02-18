package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbSeckillDiscountCouponVO;
import com.dongdongwuliu.feign.SeckillServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SeckillController
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/1 0:29
 * @Version 1.0
 **/
@Controller
@RequestMapping("seckillController")
@RefreshScope //开启自动刷新配置
@Api(value = "优惠券秒杀接口")
public class SeckillController {

    @Autowired
    private SeckillServiceFeign seckillServiceFeign;

    @RequestMapping("show")
    public String show(){
        return "/admin/seckill/list";
    }

    //查询全部优惠券
    @GetMapping("selectList")
    @ResponseBody
    @ApiOperation(value = "查询所有")
    public List<TbSeckillDiscountCouponVO> selectList() {
        DataResult<List<TbSeckillDiscountCouponDTO>> couponDTODataResult = seckillServiceFeign.selectList();
        String jsonString = JSONObject.toJSONString(couponDTODataResult.getData());
        List<TbSeckillDiscountCouponVO> list = JSONObject.parseObject(jsonString, List.class);
        return list;
    }


    @RequestMapping("toAdd")
    public String toAdd(){
        return "/admin/seckill/add";
    }


    //增加
    @RequestMapping("addSeckillInfo")
    @ResponseBody
    public DataResult addSeckillInfo(TbSeckillDiscountCouponVO tbSeckillDiscountCouponVO){
        DataResult dataResult = seckillServiceFeign.addSeckillInfo(tbSeckillDiscountCouponVO);
        return dataResult;
    }

    //删除
    @RequestMapping("deleteInfoById/{id}")
    @ResponseBody
    public Integer deleteInfoById(@PathVariable("id")Long id){
        DataResult dataResult = seckillServiceFeign.deleteInfoById(id);
        if(dataResult.getCode() == 200){
            return 1;
        }else{
            return 2;
        }
    }





}

 

