package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbProvincesDTO;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.TbAreasVO;
import com.dongdongwuliu.domain.vo.TbCitiesVO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.feign.AddressServiceFeign;
import com.dongdongwuliu.feign.TbSiteServiceFeign;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/2/4 23:00
 * @Version 1.0
 **/
@Controller
@Api(description = "网点查询接口")
@RefreshScope //开启自动刷新配置
@RequestMapping("site")
public class SiteController {
    @Autowired
    private TbSiteServiceFeign tbSiteServiceFeign;
    @Autowired
    private AddressServiceFeign addressServiceFeign;

    //查询所有的站点信息
    @RequestMapping("webSelectAll")
    @ResponseBody
    public List<TbSiteVO> webSelectAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "4") Integer pageSize){
        DataResult dataResult = tbSiteServiceFeign.webSelectAll(pageNum,pageSize);
        //将调用后传递过来的结果转换成字符串类型
        String jsonString = JSONObject.toJSONString(dataResult.getData());
        List<TbSiteVO> list = JSONObject.parseObject(jsonString, List.class);
        return list;
    }

    //搜索下拉列表框的展示所有的省
    @RequestMapping("pidList")
    @ResponseBody
    public List<TbProvincesDTO> pidList(){
        DataResult info = addressServiceFeign.getInfo();
        Object data = info.getData();
        String result = JSONObject.toJSONString(data);
        List<TbProvincesDTO> list = JSONObject.parseObject(result, List.class);
        return list;
    }

    //通过省获取市
    @RequestMapping("getCity/{provinceId}")
    @ResponseBody
    public List<TbCitiesVO> getCity(@PathVariable("provinceId")String provinceId){
        DataResult city = addressServiceFeign.getCity(Integer.valueOf(provinceId));
        Object cityData = city.getData();
        String resultCity = JSONObject.toJSONString(cityData);
        List<TbCitiesVO> listCity = JSONObject.parseObject(resultCity, List.class);
        return listCity;
    }

    //通过市获取县
    @RequestMapping("getTown/{cityid}")
    @ResponseBody
    public List<TbAreasVO> getTown(@PathVariable("cityid")String cityid){
        DataResult town = addressServiceFeign.getTown(Integer.valueOf(cityid));
        Object townData = town.getData();
        String resultTown = JSONObject.toJSONString(townData);
        List<TbAreasVO> listTown = JSONObject.parseObject(resultTown, List.class);
        return listTown;
    }

    //搜索
    @RequestMapping("searchSite")
    @ResponseBody
    public List<TbSiteVO> searchSite(TbSiteVO tbSiteVO){
        DataResult dataResult = tbSiteServiceFeign.searchSite(tbSiteVO);
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        return list;
    }

    //下单操作
    @RequestMapping("orderBySiteId/{siteId}")
    @ResponseBody
    public void orderBySiteId(@PathVariable("siteId")Integer siteId){

    }
}