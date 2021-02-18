package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbProvincesDTO;
import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.domain.vo.TbSeckillDiscountCouponVO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.domain.vo.TbUserVO;
import com.dongdongwuliu.feign.AddressServiceFeign;
import com.dongdongwuliu.feign.EsServiceFeign;
import com.dongdongwuliu.feign.SeckillServiceFeign;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/2/3 13:17
 * @Version 1.0
 **/
@Controller
@Api(description = "登录接口")
@RefreshScope //开启自动刷新配置
public class LoginController {

    @Autowired
    private AddressServiceFeign addressServiceFeign;


    @Autowired
    private SeckillServiceFeign seckillServiceFeign;

    @Autowired
    private EsServiceFeign esServiceFeign;

    /** @Description 跳转登录
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"tologin"})
    public String tologin() {
        System.out.println("跳转登录");
        return "login";
    }

    /** @Description 下单
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toCreateOrder/{siteId}"})
    public String toCreateOrder(@PathVariable("siteId")Integer siteId, Model model) {
        //查询省级目录
        DataResult dataResult = addressServiceFeign.getInfo();
        String jsonString = JSONObject.toJSONString(dataResult.getData());
        List<TbProvincesDTO> provincesList = JSONObject.parseObject(jsonString, List.class);
        model.addAttribute("provincesList",provincesList);
        return "createorder";
    }

    /** @Description 注册页面
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toRegister"})
    public String toRegister() {
        return "register";
    }

    /** @Description 订单追踪页面
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toSearch/{phoneOrOrderId}/{orderId}/{username}"})
    public String toSearch(@PathVariable("phoneOrOrderId")String phoneOrOrderId,@PathVariable("orderId") String orderId,@PathVariable("username") String username, Model model) {
        //根据用户输入的订单id或者手机号去es查询数据
        DataResult dataResult = esServiceFeign.selectOrderEs(phoneOrOrderId,orderId,username);
        String s = JSONObject.toJSONString(dataResult.getData());
        List<TbOrderVO> list = JSONObject.parseObject(s, List.class);
        model.addAttribute("orderList",list);
        return "search";
    }

    /** @Description 订单追踪页面
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toDetail"})
    public String toDetail() {
        return "order-detail";
    }



    /** @Description 个人中心
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toHomeSettingAddress"})
    public String toHomeSettingAddress() {
        return "home-setting-address";
    }

    /** @Description 我的订单
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toMyOrder"})
    public String toMyOrder() {
        return "my-order";
    }

    /** @Description 秒杀
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toSeckillIndex"})
    public String toSeckillIndex(Model model) {
        DataResult listDataResult = seckillServiceFeign.selectList1();
        List<TbSeckillDiscountCouponDTO> data = (List<TbSeckillDiscountCouponDTO>)listDataResult.getData();
        model.addAttribute("data",data);
        return "seckill-index";
    }

    /** @Description 秒杀详情
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toSeckillItem/{id}"})
    public String toSeckillItem(@PathVariable("id")Integer id, Model model) {
        DataResult<TbSeckillDiscountCouponVO> listDataResult = seckillServiceFeign.selectSeckillById(id);
        TbSeckillDiscountCouponVO data = listDataResult.getData();
        model.addAttribute("data",data);
        return "seckill-item";
    }
    /** @Description 价格查询
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toPrice"})
    public String toPrice() {
        return "price";
    }

    /** @Description 站点
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toSite"})
    public String toSite() {
        return "site";
    }

    /** @Description 公司简介
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"toOverview"})
    public String toOverview() {
        return "overview";
    }


    @RequestMapping({"toIndex"})
    public String toIndex(String key,String s, Model model) {
        String tbUser = JSONObject.toJSONString(s);
        Object parse = JSONObject.parse(tbUser);
        TbUserVO tbUserVO = JSONObject.parseObject(parse.toString(), TbUserVO.class);
        model.addAttribute("user", tbUserVO);
        //把登录态带到前端
        model.addAttribute("Userkey", key);
        return "index";
    }

}
