package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.SpecialPriceVO;
import com.dongdongwuliu.pojo.TbSpecialPrice;
import com.dongdongwuliu.service.SpecialPriceService;
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
@RequestMapping("specialPrice")
@Api(description = "专线线路价格接口")
@RefreshScope   // 开启自动刷新配置
public class SpecialPriceController {
    @Autowired
    private SpecialPriceService specialPriceService;

    private Logger logger = LoggerFactory.getLogger(SpecialPriceController.class);

    @GetMapping("getSpecialPriceList")
    @ApiOperation("查询所有专线线路运输价格")
    public DataResult getSpecialPriceList(){
        try {
            List<TbSpecialPrice> specialPriceList = specialPriceService.getSpecialPriceList();
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (specialPriceList.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            String jsonString = JSONObject.toJSONString(specialPriceList);
            List<SpecialPriceVO> specialPriceVOList = JSONObject.parseObject(jsonString, List.class);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(specialPriceVOList);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getSpecialPriceBySpecialId/{specialId}")
    @ApiOperation("根据专线线路ID查询运输价格")
    @ApiImplicitParam(name = "specialId", value = "专线线路ID", paramType = "path")
    public DataResult getSpecialPriceBySpecialId(@PathVariable("specialId")Long specialId){
        try {
            TbSpecialPrice specialPrice = specialPriceService.getSpecialPriceBySpecialId(specialId);
            if (specialPrice == null){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            SpecialPriceVO specialPriceVO = new SpecialPriceVO();
            BeanUtils.copyProperties(specialPrice, specialPriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(specialPriceVO);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("根据专线线路价格ID查询运输价格")
    @ApiImplicitParam(name = "id", value = "专线价格ID", paramType = "path")
    public DataResult getSpecialPriceById(@PathVariable("id")Long id){
        try {
            TbSpecialPrice specialPrice = specialPriceService.getSpecialPriceById(id);
            if (specialPrice == null){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            SpecialPriceVO specialPriceVO = new SpecialPriceVO();
            BeanUtils.copyProperties(specialPrice, specialPriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(specialPriceVO);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @ApiOperation("增加专线线路运输价格")
    @ApiImplicitParam(name = "specialPriceVO", value = "专线线路运输价格实体", paramType = "body",
            required = true, dataTypeClass = SpecialPriceVO.class)
    public DataResult addSpecialPrice(@RequestBody SpecialPriceVO specialPriceVO){
        try {
            TbSpecialPrice specialPrice = new TbSpecialPrice();
            BeanUtils.copyProperties(specialPriceVO, specialPrice);
            Boolean b = specialPriceService.addSpecialPrice(specialPrice);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateSpecialPrice")
    @ApiOperation("修改专线线路运输价格")
    @ApiImplicitParam(name = "specialPriceVO", value = "专线线路运输价格实体", paramType = "body",
            required = true, dataTypeClass = SpecialPriceVO.class)
    public DataResult updateSpecialPrice(@RequestBody SpecialPriceVO specialPriceVO){
        try {
            TbSpecialPrice specialPrice = new TbSpecialPrice();
            BeanUtils.copyProperties(specialPriceVO, specialPrice);
            Boolean b = specialPriceService.updateSpecialPrice(specialPrice);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据专线价格ID删除")
    @ApiImplicitParam(name = "id", value = "专线价格ID", paramType = "path")
    public DataResult deleteSpecialPriceById(@PathVariable("id")Long id){
        try {
            Boolean b = specialPriceService.deleteSpecialPriceById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteSpecialPriceBySpecialId/{specialId}")
    @ApiOperation("根据专线线路ID删除")
    @ApiImplicitParam(name = "specialId", value = "专线线路ID", paramType = "path")
    public DataResult deleteSpecialPriceBySpecialId(@PathVariable("specialId")Long specialId){
        try {
            Boolean b = specialPriceService.deleteSpecialPriceBySpecialId(specialId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
