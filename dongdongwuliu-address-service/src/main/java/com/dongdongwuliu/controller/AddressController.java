package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbAreasDTO;
import com.dongdongwuliu.domain.dto.TbCitiesDTO;
import com.dongdongwuliu.domain.dto.TbProvincesDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.pojo.TbAreas;
import com.dongdongwuliu.pojo.TbCities;
import com.dongdongwuliu.pojo.TbProvinces;
import com.dongdongwuliu.service.TbProvincesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/2/2 16:58
 * @Description:
 */
@RestController
@RequestMapping("address")
@RefreshScope //开启自动刷新配置
@Api(value = "三级联动查询")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private TbProvincesService tbProvincesService;

    @ApiOperation(value = "查询省")
    @GetMapping("/getInfo")
    public DataResult<List<TbProvincesDTO>> getInfo(){
        try {
            List<TbProvinces> provincesList = tbProvincesService.getInfo();
            List<TbProvincesDTO> dtoList = new ArrayList<>();
            provincesList.forEach(pojo -> {
                TbProvincesDTO dto = new TbProvincesDTO();
                BeanUtils.copyProperties(pojo,dto);
                dtoList.add(dto);
            });
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dtoList);
        }catch (Exception e){
            logger.error("错误：{}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "根据省份ID查询所属市")
    @ApiImplicitParam(name = "provinceId", value = "省份ID", required = true, paramType = "path",
            dataTypeClass = Integer.class)
    @GetMapping("/getCity/{provinceId}")
    public DataResult<List<TbCitiesDTO>> getCity(@PathVariable("provinceId") Integer provinceId){
        try {
            List<TbCitiesDTO> citiesDTOList = tbProvincesService.getCity(provinceId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(citiesDTOList);
        }catch (Exception e){
            logger.error("错误：{}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "根据城市ID查询所属区")
    @ApiImplicitParam(name = "cityid", value = "城市ID", required = true, paramType = "path",
            dataTypeClass = Integer.class)
    @GetMapping("/getTown/{cityid}")
    public DataResult getTown(@PathVariable("cityid") Integer cityid){
        try {
            List<TbAreasDTO> areasDTOList = tbProvincesService.getTown(cityid);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(areasDTOList);
        }catch (Exception e){
            logger.error("错误：{}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //查询所有市
    @ApiOperation(value = "查询所有市")
    @GetMapping("/getCityAll")
    public DataResult getCityAll(){
        try {
            List<TbCities> citiesList = tbProvincesService.getCityAll();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(citiesList);
        }catch (Exception e){
            logger.error("错误：{}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //查询所有县
    @ApiOperation(value = "查询所有县")
    @GetMapping("/getTownAll")
    public DataResult getTownAll(){
        try {
            List<TbAreas> areasList = tbProvincesService.getTownAll();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(areasList);
        }catch (Exception e){
            logger.error("错误：{}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //根据省主键查询
    @ApiOperation(value = "根据省ID查询")
    @ApiImplicitParam(name = "id", value = "省ID", required = true, paramType = "path",
            dataTypeClass = Integer.class)
    @GetMapping("/findProvincesById/{id}")
    public DataResult findProvincesById(@PathVariable("id") Integer id){
        try {
            TbProvinces provinces = tbProvincesService.findProvincesById(id);
            TbProvincesDTO dto = new TbProvincesDTO();
            BeanUtils.copyProperties(provinces,dto);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dto);
        }catch (Exception e){
            logger.error("错误：{}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //根据市主键查询
    @ApiOperation(value = "根据市ID查询")
    @ApiImplicitParam(name = "id", value = "市ID", required = true, paramType = "path",
            dataTypeClass = Integer.class)
    @GetMapping("/findCityById/{id}")
    public DataResult findCityById(@PathVariable("id") Integer id){
        try {
            TbCities cities = tbProvincesService.findCityById(id);
            TbCitiesDTO dto = new TbCitiesDTO();
            BeanUtils.copyProperties(cities,dto);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dto);
        }catch (Exception e){
            logger.error("错误：{}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //根据县主键查询
    @ApiOperation(value = "根据县ID查询")
    @ApiImplicitParam(name = "id", value = "县ID", required = true, paramType = "path",
            dataTypeClass = Integer.class)
    @GetMapping("/findTownById/{id}")
    public DataResult findTownById(@PathVariable("id") Integer id){
        try {
            TbAreas areas = tbProvincesService.findTownById(id);
            TbAreasDTO dto = new TbAreasDTO();
            BeanUtils.copyProperties(areas,dto);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dto);
        }catch (Exception e){
            logger.error("错误：{}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
