package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.*;
import com.dongdongwuliu.domain.vo.*;
import com.dongdongwuliu.feign.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/2/2 10:39
 * @Description:
 */
@Controller
@RequestMapping("orderController")
@RefreshScope //开启自动刷新配置
@Api(value = "订单管理")
public class TbOrderController {

    @Autowired
    private TbOrderServiceFeign tbOrderServiceFeign;

    @Autowired
    private AddressServiceFeign addressServiceFeign;

    @Autowired
    private TbSiteServiceFeign tbSiteServiceFeign;

    @Resource
    private PriceServiceFeign priceServiceFeign;

    @Autowired
    private PathServiceFeign pathServiceFeign;

    @RequestMapping("toOrderList")
    public String toOrderList(){
        return "/admin/order/list";
    }

    @RequestMapping("getInfo")
    @ResponseBody
    public List<TbOrderVO> getInfo(TbOrderVO tbOrderVO){
        DataResult dataResult = tbOrderServiceFeign.findOrderByOutTradeNoBySenderMobileByStatus(tbOrderVO);
        String jsonString = JSONObject.toJSONString(dataResult.getData());
        List<TbOrderVO> orderVOList = JSONObject.parseObject(jsonString, List.class);
        return orderVOList;
    }

    @RequestMapping("toAdd")
    public String toOrderAdd(Model model){
        //查询省级目录
        DataResult dataResult = addressServiceFeign.getInfo();
        String jsonString = JSONObject.toJSONString(dataResult.getData());
        List<TbProvincesDTO> provincesList = JSONObject.parseObject(jsonString, List.class);
        model.addAttribute("provincesList",provincesList);
        //查询货物类型
//        DataResult dataResult1 = tbOrderServiceFeign.getCargoTypeInfo();
//        String s = JSONObject.toJSONString(dataResult1.getData());
//        List<TbCargoTypeDTO> cargoTypeVOList = JSONObject.parseObject(s, List.class);
//        model.addAttribute("cargoTypeVOList",cargoTypeVOList);
        return "/admin/order/add";
    }

    //联动市
    @RequestMapping("getCity")
    @ResponseBody
    public List<TbCitiesVO> getCity(Integer provinceId){
        DataResult dataResult = addressServiceFeign.getCity(provinceId);
        String string = JSONObject.toJSONString(dataResult.getData());
        List<TbCitiesVO> citiesVOList = JSONObject.parseObject(string, List.class);
        return citiesVOList;
    }

    //联动区
    @RequestMapping("getTown")
    @ResponseBody
    public List<TbAreasVO> getTown(Integer cityid){
        DataResult dataResult = addressServiceFeign.getTown(cityid);
        String string = JSONObject.toJSONString(dataResult.getData());
        List<TbAreasVO> areasVOList = JSONObject.parseObject(string, List.class);
        return areasVOList;
    }

    //订单增加
    @RequestMapping("addOrderInfo")
    @ResponseBody
    public String addOrderInfo(TbOrderVO tbOrderVO){
        System.out.println(tbOrderVO);
        DataResult dataResult = tbOrderServiceFeign.addOrderInfo(tbOrderVO);
        System.out.println(dataResult.getMessage());
        return dataResult.getMessage();
    }

    @RequestMapping("findOrderByOrderId/{orderId}")
    public String findOrderByOrderId(@PathVariable("orderId")String orderId,Model model){
        DataResult dataResult1 = tbOrderServiceFeign.findOrderByOrderId(orderId);
        String s = JSONObject.toJSONString(dataResult1.getData());
        TbOrderVO tbOrderVO = JSONObject.parseObject(s, TbOrderVO.class);
        model.addAttribute("tbOrder",tbOrderVO);
        return "/admin/order/update";
    }

    //订单修改
    @RequestMapping("updateOrderInfo")
    @ResponseBody
    public String updateOrderInfo(TbOrderVO tbOrderVO){
        System.out.println(tbOrderVO);
        DataResult dataResult = tbOrderServiceFeign.updateOrderInfo(tbOrderVO);
        System.out.println(dataResult.getMessage());
        return dataResult.getMessage();
    }

    @RequestMapping("selectOrderDetails/{orderId}")
    public String selectOrderDetails(@PathVariable("orderId")String orderId,Model model){
        DataResult dataResult1 = tbOrderServiceFeign.findOrderByOrderId(orderId);
        String s = JSONObject.toJSONString(dataResult1.getData());
        TbOrderVO tbOrderVO = JSONObject.parseObject(s, TbOrderVO.class);
        model.addAttribute("tbOrder",tbOrderVO);
        return "/admin/order/order_details";
    }

    //删除订单
    @RequestMapping("deleteOrderByOrderId")
    @ResponseBody
    public String deleteOrderByOrderId(String id){
        Long orderId = Long.parseLong(id);
        DataResult dataResult = tbOrderServiceFeign.deleteOrder(orderId);
        System.out.println(dataResult.getMessage());
        return dataResult.getMessage();
    }

    //取消订单
    @RequestMapping("cancelOrderByOrderId")
    @ResponseBody
    public String cancelOrderByOrderId(String id){
        Long orderId = Long.parseLong(id);
        DataResult dataResult = tbOrderServiceFeign.cancelOrderByOrderId(orderId);
        System.out.println(dataResult.getMessage());
        return dataResult.getMessage();
    }

    @RequestMapping("getTownBySite")
    @ResponseBody
    public List<TbSiteDTO> getTownBySite(String aid){
        DataResult dataResult = tbSiteServiceFeign.selectByAid(aid);
        String s = JSONObject.toJSONString(dataResult.getData());
        List<TbSiteDTO> list = JSONObject.parseObject(s, List.class);
        return list;
    }

    @RequestMapping("getCityBySite")
    @ResponseBody
    public List<TbSiteDTO> getCityBySite(String cid){
        DataResult dataResult = tbSiteServiceFeign.selectByCid(cid);
        String s = JSONObject.toJSONString(dataResult.getData());
        List<TbSiteDTO> list = JSONObject.parseObject(s, List.class);
        return list;
    }

    @RequestMapping("selectCourierPriceBySiteId")
    @ResponseBody
    public CourierPriceVO selectCourierPriceBySiteId(CourierPriceVO courierPriceVO){
        DataResult courierPriceBySiteIdAndSiteScope = priceServiceFeign.getCourierPriceBySiteIdAndSiteScope(courierPriceVO);
        String s = JSONObject.toJSONString(courierPriceBySiteIdAndSiteScope.getData());
        CourierPriceVO courierPriceVO1 = JSONObject.parseObject(s, CourierPriceVO.class);
        return courierPriceVO1;
    }

    //根据寄件人省编号  收件人省编号 查询线路
    @RequestMapping("selectStartpidByInfo/{startPid}/{endPid}")
    @ResponseBody
    public TbPathVO selectStartpidByInfo(@PathVariable("startPid") Integer startPid,@PathVariable("endPid") Integer endPid){
        DataResult dataResult = pathServiceFeign.selectStartpidByInfo(startPid, endPid);
        String s = JSON.toJSONString(dataResult.getData());
        DataResult<TbPathVO> dataResult1 = JSONObject.parseObject(s, DataResult.class);
        String s1 = JSON.toJSONString(dataResult1.getData());
        TbPathVO tbPathVO = JSONObject.parseObject(s1, TbPathVO.class);
        return tbPathVO;
    }

    // 根据普通线路ID查询运输价格
    @RequestMapping("getCarriagePriceByPathId/{pathId}")
    @ResponseBody
    public CarriagePriceVO getCarriagePriceByPathId(@PathVariable("pathId") String pathId){
        Long l = Long.parseLong(pathId);
        DataResult<CarriagePriceVO> carriagePriceByPathId = priceServiceFeign.getCarriagePriceByPathId(l);
        String s = JSON.toJSONString(carriagePriceByPathId.getData());
        CarriagePriceVO carriagePriceVO = JSONObject.parseObject(s, CarriagePriceVO.class);
        return carriagePriceVO;
    }

}
