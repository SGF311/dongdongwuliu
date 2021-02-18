package com.dongdongwuliu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.pojo.TbCar;
import com.dongdongwuliu.service.TbCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/1/30 14:21
 * @Version 1.0
 **/
@RestController
@RequestMapping("carController")
@Api(description = "用户接口")
@RefreshScope //开启自动刷新配置
public class CarController {
    @Autowired
    private TbCarService carService;
    //出现异常也要打印日志
    private Logger logger = LoggerFactory.getLogger(CarController.class);

    //增加车辆信息
    @PostMapping("/insertInfoCar")
    @ApiOperation(value = "增加车辆信息")
    @ApiImplicitParam(name = "tbCarVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbCarVO.class)
    public DataResult insertInfoCar(@RequestBody TbCarVO tbCarVO){
        try {
            TbCarDTO tbCarDTO = new TbCarDTO();
            BeanUtils.copyProperties(tbCarVO,tbCarDTO);
            carService.insertInfoCar(tbCarDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("增加失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据id或者是车牌号删除车辆信息
    @DeleteMapping("/deleteByIdOrCarNumber")
    @ApiOperation(value = "删除车辆信息")
    @ApiImplicitParam(name = "tbCarVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbCarVO.class)
    public DataResult deleteByIdOrCarNumber(@RequestBody TbCarVO tbCarVO){
        try {
            TbCarDTO tbCarDTO = new TbCarDTO();
            BeanUtils.copyProperties(tbCarVO,tbCarDTO);
            carService.deleteByIdOrCarNumber(tbCarDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("删除失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据汽车id或者汽车车牌号进行修改
    @PutMapping("/updateByIdOrCarNumber")
    @ApiOperation(value = "修改车辆信息")
    @ApiImplicitParam(name = "tbCarVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbCarVO.class)
    public DataResult updateByIdOrCarNumber(@RequestBody TbCarVO tbCarVO){
        try {
            TbCarDTO tbCarDTO = new TbCarDTO();
            BeanUtils.copyProperties(tbCarVO,tbCarDTO);
            carService.updateByIdOrCarNumber(tbCarDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("修改失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }


    //根据汽车id或者汽车车牌号进行查询
    @ApiOperation(value = "根据汽车id或者汽车车牌号进行查询")
    @ApiImplicitParam(name = "tbCarVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbCarVO.class)
    @PostMapping("/selectByIdOrCarNumber")
    public DataResult selectByIdOrCarNumber(@RequestBody TbCarVO tbCarVO){
        try {
            TbCarDTO tbCarDTO = new TbCarDTO();
            BeanUtils.copyProperties(tbCarVO,tbCarDTO);
            tbCarDTO = carService.selectByIdOrCarNumber(tbCarDTO);
            BeanUtils.copyProperties(tbCarDTO,tbCarVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbCarVO);
        }catch (Exception e){
            logger.error("查询失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //前端页面展示所有及分页
    @PostMapping("/selectPage")
    @ApiOperation(value = "展示车辆信息并分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbCarVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbCarVO.class),
            @ApiImplicitParam(name = "pageNumber", value = "页数", required = true, paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataTypeClass = Integer.class)
    })
    public DataResult selectPage(@RequestBody TbCarVO tbCarVO,@RequestParam("pageNumber") Integer pageNumber,@RequestParam("pageSize") Integer pageSize){
        try {
            TbCarDTO tbCarDTO = new TbCarDTO();
            BeanUtils.copyProperties(tbCarVO,tbCarDTO);
            Map<String, Object> map = carService.selectPage(tbCarDTO, pageNumber, pageSize);
//            List<TbCar> tbCarList = list.getRecords();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(map);
        }catch (Exception e){
            logger.error("查询失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }

    }

    //可调度车辆查询
    @PostMapping("/carControlSelect")
    @ApiOperation(value = "可调度车辆查询")
    public DataResult carControlSelect(){
        try {
            //状态为 0 即空闲车辆
            Integer status = 0;
            //站点id为null时  即车辆未绑定站点时
            Integer siteId = null;
            List<TbCar> list = carService.carControlSelect(status,siteId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
        }catch (Exception e){
            logger.error("查询失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据汽车id或者汽车车牌号进行查询
    @ApiOperation(value = "根据站点id进行查询")
    @ApiImplicitParam(name = "tbCarVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbCarVO.class)
    @PostMapping("/selectBySiteId")
    public DataResult selectBySiteId(@RequestBody TbCarVO tbCarVO){
        try {
            TbCarDTO tbCarDTO = new TbCarDTO();
            BeanUtils.copyProperties(tbCarVO,tbCarDTO);
            List<TbCarDTO> tbCarDTOS = carService.selectBySiteId(tbCarDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbCarDTOS);
        }catch (Exception e){
            logger.error("查询失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

}