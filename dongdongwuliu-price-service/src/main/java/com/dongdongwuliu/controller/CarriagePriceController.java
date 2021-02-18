package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.CarriagePriceVO;
import com.dongdongwuliu.pojo.TbCarriagePrice;
import com.dongdongwuliu.service.CarriagePriceService;
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
@RequestMapping("carriagePrice")
@Api(description = "普通线路价格接口")
@RefreshScope   // 开启自动刷新配置
public class CarriagePriceController {
    @Autowired
    private CarriagePriceService carriagePriceService;

    private Logger logger = LoggerFactory.getLogger(CarriagePriceController.class);

    @GetMapping("getCarriagePriceList")
    @ApiOperation("查询所有普通线路运输价格")
    public DataResult getCarriagePriceList(){
        try {
            List<TbCarriagePrice> carriagePriceList = carriagePriceService.getCarriagePriceList();
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (carriagePriceList.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            String jsonString = JSONObject.toJSONString(carriagePriceList);
            List<CarriagePriceVO> priceVOList = JSONObject.parseObject(jsonString, List.class);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(priceVOList);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getCarriagePriceByPathId/{pathId}")
    @ApiOperation("根据普通线路ID查询运输价格")
    @ApiImplicitParam(name = "pathId", value = "普通线路ID", paramType = "path")
    public DataResult getCarriagePriceByPathId(@PathVariable("pathId")Long pathId){
        try {
            TbCarriagePrice carriagePrice = carriagePriceService.getCarriagePriceByPathId(pathId);
            if (carriagePrice == null){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            CarriagePriceVO carriagePriceVO = new CarriagePriceVO();
            BeanUtils.copyProperties(carriagePrice, carriagePriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(carriagePriceVO);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("根据普通线路价格ID查询运输价格")
    @ApiImplicitParam(name = "id", value = "普线价格ID", paramType = "path")
    public DataResult getCarriagePriceById(@PathVariable("id")Long id){
        try {
            TbCarriagePrice carriagePrice = carriagePriceService.getCarriagePriceById(id);
            if (carriagePrice == null){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            CarriagePriceVO carriagePriceVO = new CarriagePriceVO();
            BeanUtils.copyProperties(carriagePrice, carriagePriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(carriagePriceVO);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @ApiOperation("增加普通线路运输价格")
    @ApiImplicitParam(name = "carriagePriceVO", value = "普通线路运输价格实体", paramType = "body",
            required = true, dataTypeClass = CarriagePriceVO.class)
    public DataResult addCarriagePrice(@RequestBody CarriagePriceVO carriagePriceVO){
        try {
            TbCarriagePrice carriagePrice = new TbCarriagePrice();
            BeanUtils.copyProperties(carriagePriceVO, carriagePrice);
            Boolean b = carriagePriceService.addCarriagePrice(carriagePrice);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateCarriagePrice")
    @ApiOperation("修改普通线路运输价格")
    @ApiImplicitParam(name = "carriagePriceVO", value = "普通线路运输价格实体", paramType = "body",
            required = true, dataTypeClass = CarriagePriceVO.class)
    public DataResult updateCarriagePrice(@RequestBody CarriagePriceVO carriagePriceVO){
        try {
            TbCarriagePrice carriagePrice = new TbCarriagePrice();
            BeanUtils.copyProperties(carriagePriceVO, carriagePrice);
            Boolean b = carriagePriceService.updateCarriagePrice(carriagePrice);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据普通线路价格ID删除")
    @ApiImplicitParam(name = "id", value = "价格ID", paramType = "path")
    public DataResult deleteCarriagePriceById(@PathVariable("id")Long id){
        try {
            Boolean b = carriagePriceService.deleteCarriagePriceById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteCarriagePriceByPathId/{pathId}")
    @ApiOperation("根据普通线路ID删除")
    @ApiImplicitParam(name = "pathId", value = "普通线路ID", paramType = "path")
    public DataResult deleteCarriagePriceByPathId(@PathVariable("pathId")Long pathId){
        try {
            Boolean b = carriagePriceService.deleteCarriagePriceByPathId(pathId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
