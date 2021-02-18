package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbSeckillDiscountCouponVO;
import com.dongdongwuliu.domain.vo.TbUserDiscountCouponVO;
import com.dongdongwuliu.pojo.TbSeckillDiscountCoupon;
import com.dongdongwuliu.pojo.TbUserDiscountCoupon;
import com.dongdongwuliu.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName seckillWebController
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/1/30 16:04
 * @Version 1.0
 **/
@RestController
@RequestMapping("seckillWebController")
@Api
public class SeckillWebController {

    @Autowired
    private SeckillService seckillService;

    private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);

    //查询优惠券
    @ApiOperation(value = "查询全部优惠券")
    @GetMapping("/selectList1")
    public DataResult selectList1(){
        try {
            List<TbSeckillDiscountCouponDTO> list = seckillService.selectList1();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
        }catch (Exception e){
            logger.error("全查错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //提交订单
    @ApiOperation(value = "提交订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "优惠券id",required = true,dataTypeClass = Long.class),
            @ApiImplicitParam(name = "userId",value = "userId",required = true,dataTypeClass = Long.class)

    })
    @PostMapping("/submitStockCount")
    public DataResult submitStockCount(@RequestParam Long id,@RequestParam Long userId){
        try {
            seckillService.submitStockCount(id,userId);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("提交订单错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    //回显
    @ApiOperation("优惠券回显,根据id查询优惠券")
    @ApiImplicitParam(name = "id",value = "id",required = true,paramType = "path",dataTypeClass = Integer.class)
    @GetMapping("/selectSeckillById/{id}")
    public DataResult<TbSeckillDiscountCouponVO> selectSeckillById(@PathVariable("id")Integer id){
        try {
            TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO = seckillService.selectSeckillById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSeckillDiscountCouponDTO);
        }catch (Exception e){
            logger.error("回显错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }



    //根据用户id或者优惠券价格查询
    @ApiOperation(value = "根据用户id或者优惠券面值查询")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "userId",value = "userId",required = true,paramType = "path",dataTypeClass = Long.class),
         @ApiImplicitParam(name = "sumPrice",value = "sumPrice",required = true,paramType = "path",dataTypeClass = Integer.class)
    })
    @PostMapping("/selectSeckillByUserIdBySumPrice/{userId}/{sumPrice}")
    public DataResult<TbUserDiscountCoupon> selectSeckillByUserIdBySumPrice(@PathVariable("userId")Long userId,@PathVariable("sumPrice")Integer sumPrice){
        try {
            List<TbUserDiscountCoupon> tbSeckillDiscountCoupon = seckillService.selectSeckillByUserIdBySumPrice(userId,sumPrice);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSeckillDiscountCoupon);
        }catch (Exception e){
            logger.error("回显错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }



    //根据优惠券id进行修改
    @PutMapping("/updateById")
    @ApiOperation(value = "修改优惠券信息")
    @ApiImplicitParam(name = "tbUserDiscountCoupon", value = "实体类", required = true, paramType = "body", dataTypeClass = TbUserDiscountCouponVO.class)
    public DataResult updateById(@RequestBody TbUserDiscountCouponVO tbUserDiscountCoupon){
        try {
            seckillService.updateById(tbUserDiscountCoupon);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("修改失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }



}

 

