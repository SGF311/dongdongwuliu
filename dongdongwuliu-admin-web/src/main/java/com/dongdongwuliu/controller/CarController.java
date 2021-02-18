package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.feign.TbCarServiceFeign;
import com.dongdongwuliu.feign.TbSiteServiceFeign;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/2/1 12:25
 * @Version 1.0
 **/
@Controller
@RequestMapping("car")
public class CarController {

    @Autowired
    private TbCarServiceFeign tbCarServiceFeign;
    @Autowired
    private TbSiteServiceFeign tbSiteServiceFeign;

    @RequestMapping("toCar")
    public String toCar(Model model){
        DataResult dataResult = tbSiteServiceFeign.selectAll();
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        model.addAttribute("list",list);
        return "admin/car/list";
    }

    //调用后台的controller 查询所有数据 根据汽车id或者汽车车牌号进行查询
    @RequestMapping("selectPage")
    @ResponseBody
    public Map<String,Object> selectPage(TbCarVO tbCarVO,@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "4") Integer pageSize){
        TbCarDTO tbCarDTO = new TbCarDTO();
        BeanUtils.copyProperties(tbCarVO,tbCarDTO);
        DataResult listDataResult = tbCarServiceFeign.selectPage(tbCarDTO, pageNumber, pageSize);
        //将调用后传递过来的结果转换成字符串类型
        String jsonString = JSONObject.toJSONString(listDataResult.getData());
        Map map = JSONObject.parseObject(jsonString, Map.class);
        return map;
    }

//    //根据汽车id或者汽车车牌号进行查询
//    @RequestMapping("selectByIdOrCarNumber")
//    @ResponseBody
//    public TbCarVO selectByIdOrCarNumber(TbCarVO tbCarVO){
//        TbCarDTO tbCarDTO = new TbCarDTO();
//        BeanUtils.copyProperties(tbCarVO,tbCarDTO);
//        DataResult<TbCarVO> tbCarVODataResult = tbCarServiceFeign.selectByIdOrCarNumber(tbCarDTO);
//        TbCarVO carVO = tbCarVODataResult.getData();
//        return carVO;
//    }
    //添加汽车信息
    @RequestMapping("toAdd")
    public String addInfo(Model model){
        DataResult dataResult = tbSiteServiceFeign.selectAll();
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        model.addAttribute("list",list);
        return "admin/car/add";
    }

    @RequestMapping("addCarInfo")
    @ResponseBody
    public Integer addCarInfo(TbCarVO tbCarVO){
        DataResult dataResult = tbCarServiceFeign.insertInfoCar(tbCarVO);
        return dataResult.getCode();
    }

    //公司人员 根据id修改车辆信息  先进行查询
    @RequestMapping("toUpdate/{carId}")
    public String selectByIdOrCarNumber(@PathVariable("carId")Integer carId, Model model){
        TbCarDTO tbCarDTO = new TbCarDTO();
        tbCarDTO.setCarId(carId);
        DataResult<TbCarVO> tbCarVODataResult = tbCarServiceFeign.selectByIdOrCarNumber(tbCarDTO);
        TbCarVO carVO = tbCarVODataResult.getData();
        model.addAttribute("carVO",carVO);
        //回显所有的站点信息
        DataResult dataResult = tbSiteServiceFeign.selectAll();
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        model.addAttribute("list",list);
        return "admin/car/update";
    }

    //站点人员  更改汽车运行状态
    @RequestMapping("updateCarStatus/{carId}")
    public String updateCarStatus(@PathVariable("carId")Integer carId, Model model){
        TbCarDTO tbCarDTO = new TbCarDTO();
        tbCarDTO.setCarId(carId);
        DataResult<TbCarVO> tbCarVODataResult = tbCarServiceFeign.selectByIdOrCarNumber(tbCarDTO);
        TbCarVO carVO = tbCarVODataResult.getData();
        model.addAttribute("carVO",carVO);
        //回显所有的站点信息
        DataResult dataResult = tbSiteServiceFeign.selectAll();
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        model.addAttribute("list",list);
        return "admin/car/updateStatus";
    }

    @RequestMapping("updateCarInfo")
    @ResponseBody
    public Integer updateCarInfo(TbCarVO carVO){
        DataResult dataResult = tbCarServiceFeign.updateByIdOrCarNumber(carVO);
        return dataResult.getCode();
    }

    //删除数据
    @RequestMapping("dropInfo")
    @ResponseBody
    public Integer dropInfo(TbCarVO carVO){
        DataResult dataResult = tbCarServiceFeign.deleteByIdOrCarNumber(carVO);
        return dataResult.getCode();
    }

    //可调度车辆查询
    @RequestMapping("carControl")
    public String carControl(Model model){
        DataResult dataResult = tbSiteServiceFeign.selectAll();
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        model.addAttribute("list",list);
        return "admin/car/carControl";
    }
    @RequestMapping("carControlSelect")
    @ResponseBody
    public List<TbSiteVO> carControlSelect(){
        DataResult dataResult = tbCarServiceFeign.carControlSelect();
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        return list;
    }

    //更改车辆站点
    @RequestMapping("toUpdateCarSite/{carId}")
    public String updateCarSite(@PathVariable("carId")Integer carId, Model model){
        TbCarDTO tbCarDTO = new TbCarDTO();
        tbCarDTO.setCarId(carId);
        DataResult<TbCarVO> tbCarVODataResult = tbCarServiceFeign.selectByIdOrCarNumber(tbCarDTO);
        TbCarVO carVO = tbCarVODataResult.getData();
        model.addAttribute("carVO",carVO);
        //回显所有的站点信息
        DataResult dataResult = tbSiteServiceFeign.selectAll();
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        model.addAttribute("list",list);
        return "admin/car/updateCarSite";
    }

}