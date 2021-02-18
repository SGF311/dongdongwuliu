package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.ConnectVO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.feign.ConnectServiceFeign;
import com.dongdongwuliu.feign.TbSiteServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("connectController")
public class TbConnectController {
    @Resource
    private ConnectServiceFeign connectServiceFeign;
    @Resource
    private TbSiteServiceFeign siteServiceFeign;

    private Logger logger = LoggerFactory.getLogger(TbConnectController.class);

    // 跳转到订单的物流信息展示页面
    @RequestMapping("toConnect")
    public String toConnect(Model model){
        DataResult<List<TbSiteVO>> dataResult = siteServiceFeign.selectAll();
        model.addAttribute("siteList", dataResult.getData());
        return "admin/connect/list";
    }

    // 跳转到订单的物流信息增加页面
    @RequestMapping("toAdd")
    public String toAdd(String orderId, Model model){
        model.addAttribute("orderId", orderId);
        return "admin/connect/insert";
    }

    // 查询所有订单的物流信息
    @RequestMapping("getConnectList")
    @ResponseBody
    public DataResult getConnectList(){
        try {
            DataResult<List<ConnectVO>> dataResult = connectServiceFeign.getConnectList();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 增加订单的物流信息
    @RequestMapping("addConnect")
    @ResponseBody
    public DataResult addConnect(ConnectVO connectVO){
        try {
            DataResult dataResult = connectServiceFeign.addConnect(connectVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID查询订单物流信息, 并跳转到修改页面
    @RequestMapping("getConnectById")
    public String getConnectById(String id, Model model){
        DataResult<ConnectVO> dataResult = connectServiceFeign.getConnectById(id);
        model.addAttribute("connect", dataResult.getData());
        return "admin/connect/update";
    }

    // 根据ID删除订单物流信息
    @RequestMapping("deleteConnectById")
    @ResponseBody
    public DataResult deleteConnectById(String id){
        try {
            DataResult dataResult = connectServiceFeign.deleteConnectById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据订单ID查询物流信息,并倒叙
    @RequestMapping("getConnectByOrderId")
    public String getConnectByOrderId(String orderId, Model model){
        DataResult<List<ConnectVO>> dataResult = connectServiceFeign.getConnectByOrderId(orderId);
        model.addAttribute("list", dataResult.getData());
        return "admin/connect/addConnect";
    }

    // 跳转到订单的物流信息增加页面，增加配送人信息
    @RequestMapping("addConnectBy")
    public String addConnectBy(String orderId, Model model){
        model.addAttribute("orderId", orderId);
        return "admin/connect/add";
    }

}
