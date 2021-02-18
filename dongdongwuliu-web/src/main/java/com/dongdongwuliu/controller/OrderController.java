package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.*;
import com.dongdongwuliu.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/2/4 23:09
 * @Description:
 */
@Controller
@RequestMapping("orderController")
public class OrderController {

    @Autowired
    private TbSiteServiceFeign tbSiteServiceFeign;

    @Resource
    private PriceServiceFeign priceServiceFeign;

    @Autowired
    private PathServiceFeign pathServiceFeign;

    @Autowired
    private TbOrderServiceFeign tbOrderServiceFeign;

    @Autowired
    private EsServiceFeign esServiceFeign;

    @Autowired
    private SeckillServiceFeign seckillServiceFeign;

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

    //下单
    @RequestMapping("addOrderInfo")
    @ResponseBody
    public String addOrderInfo(TbOrderVO  tbOrderVO){
        //判断用户是否登录  登录
        tbOrderServiceFeign.fastAddOrderInfo(tbOrderVO);
        return "1";
    }

    //我的订单数据
    //将es中的数据查询
    @RequestMapping("getEsOrderInfo")
    @ResponseBody
    public List<TbOrderVO> getEsOrderInfo(String username){
        DataResult<List<TbOrderVO>> dataResult = esServiceFeign.getEsOrderInfo(username);
        String s = JSONObject.toJSONString(dataResult.getData());
        List<TbOrderVO> list = JSONObject.parseObject(s, List.class);
        return list;
    }

    //搜索订单
    //将es中的数据查询
    @RequestMapping("getEsOrderByPhoneByOrderId")
    @ResponseBody
    public List<TbOrderVO> getEsOrderByPhoneByOrderId(String senderMobile,String orderId){
        DataResult<List<TbOrderVO>> dataResult = esServiceFeign.getEsOrderByPhoneByOrderId(senderMobile,orderId);
        String s = JSONObject.toJSONString(dataResult.getData());
        List<TbOrderVO> list = JSONObject.parseObject(s, List.class);
        return list;
    }

    //查询优惠券
    @RequestMapping("selectSeckillByUserIdBySumPrice/{userId}/{sumPrice}")
    @ResponseBody
    public List<TbUserDiscountCouponVO> selectSeckillByUserIdBySumPrice(@PathVariable("userId")Long userId,@PathVariable("sumPrice")Integer sumPrice){
        DataResult dataResult = seckillServiceFeign.selectSeckillByUserIdBySumPrice(userId,sumPrice);
        String s = JSONObject.toJSONString(dataResult.getData());
        List<TbUserDiscountCouponVO> list = JSONObject.parseObject(s, List.class);
        return list;
    }

}
