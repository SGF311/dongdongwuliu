package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbOrderDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.service.TbOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/1/30 14:23
 * @Description:
 */
@RestController
@RequestMapping("order")
@Api(description = "订单接口")
@RefreshScope //开启自动刷新配置
public class TbOrderController {

    private static final Logger logger = LoggerFactory.getLogger(TbOrderController.class);

    @Autowired
    private TbOrderService tbOrderService;

    @ApiOperation(value = "查询订单")
    @GetMapping("/getInfo")
    public DataResult getInfo(){
        try {
            List<TbOrderVO> orderList = tbOrderService.getInfo();
            logger.info("查询收发货信息成功" + orderList);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(orderList);
        }catch (Exception e){
            logger.error("错误：" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "下单")
    @ApiImplicitParam(name = "tbOrderVO", value = "订单实体", required = true, paramType = "body",
            dataTypeClass = TbOrderVO.class)
    @PostMapping("/addOrderInfo")
    public DataResult addOrderInfo(@RequestBody TbOrderVO tbOrderVO){
        try {
            TbOrderDTO tbOrderDTO = new TbOrderDTO();
            BeanUtils.copyProperties(tbOrderVO,tbOrderDTO);
            tbOrderService.addOrderInfo(tbOrderDTO);
            logger.info("下单成功");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "订单详情")
    @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "path",
            dataTypeClass = String.class)
    @PostMapping("/findOrderByOrderId/{orderId}")
    public DataResult findOrderByOrderId(@PathVariable(value = "orderId") String orderId){
        try {
            Long l = Long.parseLong(orderId);
            TbOrderVO order = tbOrderService.findOrderByOrderId(l);
            logger.info("根据订单id查找出数据:" + order);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(order);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "修改订单")
    @ApiImplicitParam(name = "tbOrderVO", value = "需要修改的订单信息", required = true, paramType = "body",
            dataTypeClass = TbOrderVO.class)
    @PutMapping("/updateOrderInfo")
    public DataResult updateOrderInfo(@RequestBody TbOrderVO tbOrderVO){
        try {
            TbOrderDTO tbOrderDTO = new TbOrderDTO();
            BeanUtils.copyProperties(tbOrderVO,tbOrderDTO);
            tbOrderService.updateOrderInfo(tbOrderDTO);
            logger.info("修改订单成功");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "订单查询")
    @ApiImplicitParam(name = "tbOrderVO", value = "根据订单单号，或者手机号，或者订单状态", required = true, paramType = "body",
            dataTypeClass = TbOrderVO.class)
    @PostMapping("/findOrderByOutTradeNoBySenderMobileByStatus")
    public DataResult findOrderByOutTradeNoBySenderMobileByStatus(@RequestBody TbOrderVO tbOrderVO){
        try {
            List<TbOrderVO> order = tbOrderService.findOrderByOutTradeNoBySenderMobileByStatus(tbOrderVO);
            logger.info("订单查询成功");
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(order);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "订单删除")
    @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "path",
            dataTypeClass = Long.class)
    @DeleteMapping("/deleteOrder/{orderId}")
    public DataResult deleteOrder(@PathVariable("orderId") Long orderId){
        try {
            tbOrderService.deleteOrder(orderId);
            logger.info("删除订单成功");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "取消订单")
    @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "path",
            dataTypeClass = Long.class)
    @DeleteMapping("/cancelOrderByOrderId/{orderId}")
    public DataResult cancelOrderByOrderId(@PathVariable("orderId") Long orderId){
        try {
            tbOrderService.cancelOrderByOrderId(orderId);
            logger.info("删除订单成功");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "定时查询配送员订单信息")
    @GetMapping("/selectVisitTime")
    public DataResult selectVisitTime(){
        try {
            tbOrderService.selectVisitTime();
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //------------------------------------------------

    //前台功能

    @ApiOperation(value = "快速下单")
    @ApiImplicitParam(name = "tbOrderVO", value = "订单实体", required = true, paramType = "body",
            dataTypeClass = TbOrderVO.class)
    @PostMapping("/fastAddOrderInfo")
    public DataResult fastAddOrderInfo(@RequestBody TbOrderVO tbOrderVO){
        try {
            TbOrderDTO tbOrderDTO = new TbOrderDTO();
            BeanUtils.copyProperties(tbOrderVO,tbOrderDTO);
            tbOrderService.fastAddOrderInfo(tbOrderDTO);
            logger.info("下单成功");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //支付成功修改状态
    @ApiOperation(value = "支付成功修改订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = false, paramType = "query",
            dataTypeClass = String.class),
            @ApiImplicitParam(name = "orderId", value = "订单号", required = true, paramType = "query",
            dataTypeClass = String.class)
    })
    @PutMapping("/updateStatus")
    public DataResult updateStatus(@RequestParam String username,@RequestParam String orderId){
        try {
            tbOrderService.updateStatus(username,orderId);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
