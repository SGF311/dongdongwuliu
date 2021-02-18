package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbAreasDTO;
import com.dongdongwuliu.domain.dto.TbCitiesDTO;
import com.dongdongwuliu.domain.dto.TbProvincesDTO;
import com.dongdongwuliu.feign.AddressServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("provincesController")
public class ProvincesController {
    @Resource
    private AddressServiceFeign addressServiceFeign;

    private Logger logger = LoggerFactory.getLogger(ProvincesController.class);

    // 获取所有省
    @RequestMapping("getInfo")
    @ResponseBody
    public DataResult getInfo(){
        try {
            DataResult<List<TbProvincesDTO>> dataResult = addressServiceFeign.getInfo();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据省ID查询所有市
    @RequestMapping("getCity")
    @ResponseBody
    public DataResult getCity(Integer provinceId){
        try {
            DataResult<List<TbCitiesDTO>> dataResult = addressServiceFeign.getCity(provinceId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据市ID查询所有县/区
    @RequestMapping("getTown")
    @ResponseBody
    public DataResult getTown(Integer cityid){
        try {
            DataResult<List<TbAreasDTO>> dataResult = addressServiceFeign.getTown(cityid);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
