package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbAreasDTO;
import com.dongdongwuliu.domain.dto.TbCitiesDTO;
import com.dongdongwuliu.domain.dto.TbProvincesDTO;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.TbAreasVO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbCitiesVO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.feign.AddressServiceFeign;
import com.dongdongwuliu.feign.TbSiteServiceFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/2/2 17:13
 * @Version 1.0
 **/
@Controller
@RequestMapping("site")
public class SiteController {

    @Autowired
    private TbSiteServiceFeign tbSiteServiceFeign;
    @Autowired
    private AddressServiceFeign addressServiceFeign;

    @RequestMapping("toSite")
    public String toSite(Model model){
        //获取省
        DataResult info = addressServiceFeign.getInfo();
        Object procince = info.getData();
        String result = JSONObject.toJSONString(procince);
        List<TbProvincesDTO> listTbProvinces = JSONObject.parseObject(result, List.class);
        model.addAttribute("listTbProvinces",listTbProvinces);
        //获取市
        DataResult cityAll = addressServiceFeign.getCityAll();
        Object city = cityAll.getData();
        String resultCity = JSONObject.toJSONString(city);
        List<TbCitiesDTO> listTbCity = JSONObject.parseObject(resultCity, List.class);
        model.addAttribute("listTbCity",listTbCity);
        //获取县
        DataResult townAll = addressServiceFeign.getTownAll();
        Object town = townAll.getData();
        String resultTown = JSONObject.toJSONString(town);
        List<TbAreasDTO> listTbArea = JSONObject.parseObject(resultTown, List.class);
        model.addAttribute("listTbArea",listTbArea);
        return "admin/site/list";
    }
    //三级联动获取  市信息
    @RequestMapping("getCity/{provinceId}")
    @ResponseBody
    public List<TbCitiesVO> getCity(@PathVariable("provinceId")String provinceId){
        DataResult city = addressServiceFeign.getCity(Integer.valueOf(provinceId));
        Object cityData = city.getData();
        String resultCity = JSONObject.toJSONString(cityData);
        List<TbCitiesVO> listCity = JSONObject.parseObject(resultCity, List.class);
        return listCity;
    }
    //三级联动获取  县信息
    @RequestMapping("getTown/{cityid}")
    @ResponseBody
    public List<TbAreasVO> getTown(@PathVariable("cityid")String cityid){
        DataResult town = addressServiceFeign.getTown(Integer.valueOf(cityid));
        Object townData = town.getData();
        String resultTown = JSONObject.toJSONString(townData);
        List<TbAreasVO> listTown = JSONObject.parseObject(resultTown, List.class);
        return listTown;
    }

    //查询  根据站点名称或则地址进行查询
    @RequestMapping("selectByIdOrSiteName")
    @ResponseBody
    public Map<String,Object> selectByIdOrSiteName(TbSiteVO tbSiteVO,@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "4") Integer pageSize){
        TbSiteDTO tbSiteDTO = new TbSiteDTO();
        BeanUtils.copyProperties(tbSiteVO,tbSiteDTO);
        DataResult dataResult = tbSiteServiceFeign.selectByIdOrSiteName(tbSiteDTO,pageNumber,pageSize);
        //将调用后传递过来的结果转换成字符串类型
        String jsonString = JSONObject.toJSONString(dataResult.getData());
        Map map = JSONObject.parseObject(jsonString, Map.class);
        return map;
    }

    //增加
    @RequestMapping("toAdd")
    public String toAdd(Model model){
        //获取省
        DataResult info = addressServiceFeign.getInfo();
        Object data = info.getData();
        String result = JSONObject.toJSONString(data);
        List<TbProvincesDTO> listTbProvinces = JSONObject.parseObject(result, List.class);
        model.addAttribute("listTbProvinces",listTbProvinces);
        return "admin/site/add";
    }


    @RequestMapping("addSiteInfo")
    @ResponseBody
    public Integer addSiteInfo(TbSiteVO tbSiteVO){
        TbSiteDTO tbSiteDTO = new TbSiteDTO();
        BeanUtils.copyProperties(tbSiteVO,tbSiteDTO);
        DataResult dataResult = tbSiteServiceFeign.addSiteInfo(tbSiteDTO);
        return dataResult.getCode();
    }

    //修改之前的查询  查询需要修改的对象
    @RequestMapping("toUpdate/{siteId}")
    public String toUpdate(@PathVariable("siteId")Integer siteId, Model model){
        DataResult dataResult = tbSiteServiceFeign.toUpdate(siteId);
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        TbSiteVO tbSiteVO = JSONObject.parseObject(result, TbSiteVO.class);
        model.addAttribute("tbSiteVO",tbSiteVO);
        //获取省
        DataResult info = addressServiceFeign.getInfo();
        Object procince = info.getData();
        String resultPro = JSONObject.toJSONString(procince);
        List<TbProvincesDTO> listTbProvinces = JSONObject.parseObject(resultPro, List.class);
        model.addAttribute("listTbProvinces",listTbProvinces);
        //获取市
        DataResult cityAll = addressServiceFeign.getCityAll();
        Object city = cityAll.getData();
        String resultCity = JSONObject.toJSONString(city);
        List<TbCitiesDTO> listTbCity = JSONObject.parseObject(resultCity, List.class);
        model.addAttribute("listTbCity",listTbCity);
        //获取县
        DataResult townAll = addressServiceFeign.getTownAll();
        Object town = townAll.getData();
        String resultTown = JSONObject.toJSONString(town);
        List<TbAreasDTO> listTbArea = JSONObject.parseObject(resultTown, List.class);
        model.addAttribute("listTbArea",listTbArea);
        return "admin/site/update";
    }

    @RequestMapping("updateSiteInfo")
    @ResponseBody
    public Integer updateSiteInfo(TbSiteVO tbSiteVO){
        DataResult dataResult = tbSiteServiceFeign.updateInfoSite(tbSiteVO);
        return dataResult.getCode();
    }

    //删除
    @RequestMapping("dropInfo/{siteId}")
    @ResponseBody
    public Integer dropInfo(@PathVariable("siteId") Integer siteId){
        System.out.println(siteId);
        DataResult dataResult = tbSiteServiceFeign.deleteBySiteId(siteId);
        return dataResult.getCode();
    }

    //查询全部的数据
    @RequestMapping("selectAll")
    @ResponseBody
    public List<TbSiteVO> selectAll(){
        DataResult dataResult = tbSiteServiceFeign.selectAll();
        Object data = dataResult.getData();
        String result = JSONObject.toJSONString(data);
        List<TbSiteVO> list = JSONObject.parseObject(result, List.class);
        return list;
    }

    //

}