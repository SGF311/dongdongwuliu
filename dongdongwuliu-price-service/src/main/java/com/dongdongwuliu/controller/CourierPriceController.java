package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.CourierPriceVO;
import com.dongdongwuliu.pojo.CourierPrice;
import com.dongdongwuliu.service.CourierPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courierPrice")
@Api(description = "配送价格接口")
@RefreshScope   // 开启自动刷新配置
public class CourierPriceController {
    @Autowired
    private CourierPriceService courierPriceService;

    private Logger logger = LoggerFactory.getLogger(CourierPriceController.class);

    @GetMapping("getCourierPriceList")
    @ApiOperation("查询所有配送价格")
    public DataResult getCourierPriceList(){
        try {
            List<CourierPrice> courierPriceList = courierPriceService.getCourierPriceList();
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (courierPriceList.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            String jsonString = JSONObject.toJSONString(courierPriceList);
            List<CourierPriceVO> courierPriceVOList = JSONObject.parseObject(jsonString, List.class);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(courierPriceVOList);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getCourierPriceBySiteId/{siteId}")
    @ApiOperation("根据站点ID查询配送价格")
    @ApiImplicitParam(name = "siteId", value = "站点ID", paramType = "path")
    public DataResult getCourierPriceBySiteId(@PathVariable("siteId")Integer siteId){
        try {
            List<CourierPrice> courierPriceList = courierPriceService.getCourierPriceBySiteId(siteId);
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (courierPriceList.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            String jsonString = JSONObject.toJSONString(courierPriceList);
            List<CourierPriceVO> courierPriceVOList = JSONObject.parseObject(jsonString, List.class);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(courierPriceVOList);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("getCourierPriceBySiteIdAndSiteScope")
    @ApiOperation("根据站点ID以及配送距离查询配送价格")
    @ApiImplicitParam(name = "courierPriceVO", value = "配送价格实体", paramType = "body",
            required = true, dataTypeClass = CourierPriceVO.class)
    public DataResult getCourierPriceBySiteIdAndSiteScope(@RequestBody CourierPriceVO courierPriceVO){
        try {
            CourierPrice courierPrice = new CourierPrice();
            BeanUtils.copyProperties(courierPriceVO, courierPrice);
            CourierPrice price = courierPriceService.getCourierPriceBySiteIdAndSiteScope(courierPrice);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(price);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询配送价格")
    @ApiImplicitParam(name = "id", value = "ID", paramType = "path")
    public DataResult getCourierPriceById(@PathVariable("id")Long id){
        try {
            CourierPrice courierPrice = courierPriceService.getCourierPriceById(id);
            if (courierPrice == null){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            CourierPriceVO courierPriceVO = new CourierPriceVO();
            BeanUtils.copyProperties(courierPrice, courierPriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(courierPriceVO);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @ApiOperation("增加配送价格")
    @ApiImplicitParam(name = "courierPriceVO", value = "配送价格实体", paramType = "body",
            required = true, dataTypeClass = CourierPriceVO.class)
    public DataResult addCourierPrice(@RequestBody CourierPriceVO courierPriceVO){
        try {
            CourierPrice courierPrice = new CourierPrice();
            BeanUtils.copyProperties(courierPriceVO, courierPrice);
            Boolean b = courierPriceService.addCourierPrice(courierPrice);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateCourierPrice")
    @ApiOperation("根据ID修改配送价格")
    @ApiImplicitParam(name = "courierPriceVO", value = "配送价格实体", paramType = "body",
            required = true, dataTypeClass = CourierPriceVO.class)
    public DataResult updateCourierPrice(@RequestBody CourierPriceVO courierPriceVO){
        try {
            CourierPrice courierPrice = new CourierPrice();
            BeanUtils.copyProperties(courierPriceVO, courierPrice);
            Boolean b = courierPriceService.updateCourierPrice(courierPrice);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", paramType = "path")
    public DataResult deleteCourierPriceById(@PathVariable("id")Long id){
        try {
            Boolean b = courierPriceService.deleteCourierPriceById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteCourierPriceBySiteId/{siteId}")
    @ApiOperation("根据站点ID删除")
    @ApiImplicitParam(name = "siteId", value = "站点ID", paramType = "path")
    public DataResult deleteCourierPriceBySiteId(@PathVariable("siteId")Integer siteId){
        try {
            Boolean b = courierPriceService.deleteCourierPriceBySiteId(siteId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
