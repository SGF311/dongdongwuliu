package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.pojo.TbSeckillDiscountCoupon;
import com.dongdongwuliu.service.SeckillService;
import com.dongdongwuliu.domain.vo.TbSeckillDiscountCouponVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SeckillController
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/1/30 10:54
 * @Version 1.0
 **/
@RestController
@RequestMapping("seckillController")
@Api
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);

    @ApiOperation(value = "查询全部")
    @GetMapping("selectList")
    public DataResult<TbSeckillDiscountCouponVO> selectList(){
        try {
            //查询
            List<TbSeckillDiscountCouponDTO> list = seckillService.selectList();
            //定义一个vo数组
            List<TbSeckillDiscountCouponVO> list1 = new ArrayList<>();
            //转换
            BeanUtils.copyProperties(list,list1);

            //将dto转换为vo
            list.forEach((dto) -> {
                TbSeckillDiscountCouponVO seckillDiscountCouponVO = new TbSeckillDiscountCouponVO();
                BeanUtils.copyProperties(dto,seckillDiscountCouponVO);
                list1.add(seckillDiscountCouponVO);
            });
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list1);
        }catch (Exception e){
            logger.error("全查错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("优惠券增加,根据实体")
    @ApiImplicitParam(name = "tbSeckillDiscountCouponVO",value = "优惠券实体",paramType = "body",required = true,dataTypeClass = TbSeckillDiscountCouponVO.class)
    @PostMapping("/addSeckillInfo")
    public DataResult addSeckillInfo(@RequestBody TbSeckillDiscountCouponVO tbSeckillDiscountCouponVO){
        try {
            TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO = new TbSeckillDiscountCouponDTO();
            BeanUtils.copyProperties(tbSeckillDiscountCouponVO,tbSeckillDiscountCouponDTO);
            seckillService.addSeckillInfo(tbSeckillDiscountCouponDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("增加错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    //回显
    @ApiOperation("优惠券回显,根据id查询优惠券")
    @ApiImplicitParam(name = "id",value = "id",required = true,paramType = "path",dataTypeClass = Integer.class)
    @GetMapping("/{id}")
    public DataResult<TbSeckillDiscountCouponVO> selectSeckillById(@PathVariable("id")Integer id){
        try {
            TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO = seckillService.selectSeckillById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSeckillDiscountCouponDTO);
        }catch (Exception e){
            logger.error("回显错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("优惠券修改,根据id修改优惠券,实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "优惠券id",required = true,paramType = "path",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "tbSeckillDiscountCoupon",value = "优惠券实体",required = true,dataTypeClass = TbSeckillDiscountCoupon.class)
    })
    @PutMapping("/updateSeckillByInfo/id")
    public DataResult updateSeckillByInfo(@PathVariable("id")Long id ,TbSeckillDiscountCouponVO tbSeckillDiscountCouponVO){
        try {
            tbSeckillDiscountCouponVO.setId(id);
            TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO = new TbSeckillDiscountCouponDTO();
            BeanUtils.copyProperties(tbSeckillDiscountCouponVO,tbSeckillDiscountCouponDTO);
            seckillService.updateSeckillByInfo(tbSeckillDiscountCouponDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("修改错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("优惠券删除,根据id删除优惠券")
    @ApiImplicitParam(name = "id", value = "优惠券id", required = true, paramType = "path",
            dataTypeClass = Long.class)
    @DeleteMapping("/deleteInfoById/{id}")
    public DataResult deleteInfoById(@PathVariable("id")Long id){
        try {
//            TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO = new TbSeckillDiscountCouponDTO();
//            BeanUtils.copyProperties(tbSeckillDiscountCouponVO,tbSeckillDiscountCouponDTO);
//            Integer i = seckillService.deleteInfoById(tbSeckillDiscountCouponDTO);
            Integer i = seckillService.deleteInfoById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(i);
        }catch (Exception e){
            logger.error("删除错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}

 

