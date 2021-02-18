package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.ConnectVO;
import com.dongdongwuliu.pojo.Connect;
import com.dongdongwuliu.service.ConnectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("connect")
@Api(description = "交接单接口")
@RefreshScope   // 开启自动刷新配置
public class ConnectController {
    @Autowired
    private ConnectService connectService;

    private Logger logger = LoggerFactory.getLogger(ConnectController.class);

    @GetMapping("getConnectList")
    @ApiOperation("查询所有订单的物流信息")
    public DataResult getConnectList(){
        try {
            List<Connect> connectList = connectService.getConnectList();
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (connectList.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            String jsonString = JSONObject.toJSONString(connectList);
            List<ConnectVO> connectVOList = JSONObject.parseObject(jsonString, List.class);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(connectVOList);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getConnectByOrderId/{orderId}")
    @ApiOperation("根据订单ID查询物流信息,并倒叙")
    @ApiImplicitParam(name = "orderId", value = "订单ID", paramType = "path")
    public DataResult getConnectByOrderId(@PathVariable("orderId")String orderId){
        try {
            List<Connect> connectList = connectService.getConnectByOrderId(orderId);
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (connectList.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            String jsonString = JSONObject.toJSONString(connectList);
            List<ConnectVO> connectVOList = JSONObject.parseObject(jsonString, List.class);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(connectVOList);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询订单物流信息")
    @ApiImplicitParam(name = "id", value = "交接单ID", paramType = "path")
    public DataResult getConnectById(@PathVariable("id")String id){
        try {
            Connect connect = connectService.getConnectById(id);
            if (connect == null){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            ConnectVO connectVO = new ConnectVO();
            BeanUtils.copyProperties(connect, connectVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(connectVO);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @ApiOperation("增加订单的物流信息")
    @ApiImplicitParam(name = "connectVO", value = "订单的物流信息实体", paramType = "body",
            required = true, dataTypeClass = ConnectVO.class)
    public DataResult addConnect(@RequestBody ConnectVO connectVO){
        try {
            Connect connect = new Connect();
            BeanUtils.copyProperties(connectVO, connect);
            Boolean b = connectService.addConnect(connect);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateConnect")
    @ApiOperation("修改订单的物流信息")
    @ApiImplicitParam(name = "connectVO", value = "订单的物流信息实体", paramType = "body",
            required = true, dataTypeClass = ConnectVO.class)
    public DataResult updateConnect(@RequestBody ConnectVO connectVO){
        try {
            Connect connect = new Connect();
            BeanUtils.copyProperties(connectVO, connect);
            Boolean b = connectService.updateConnect(connect);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据ID删除订单物流信息")
    @ApiImplicitParam(name = "id", value = "ID", paramType = "path")
    public DataResult deleteConnectById(@PathVariable("id")String id){
        try {
            Boolean b = connectService.deleteConnectById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteConnectByOrderId/{orderId}")
    @ApiOperation("根据订单ID删除订单物流信息")
    @ApiImplicitParam(name = "orderId", value = "订单ID", paramType = "path")
    public DataResult deleteConnectByOrderId(@PathVariable("orderId")String orderId){
        try {
            Boolean b = connectService.deleteConnectByOrderId(orderId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
