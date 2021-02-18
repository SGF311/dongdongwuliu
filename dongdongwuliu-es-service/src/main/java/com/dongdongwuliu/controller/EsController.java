package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbOrderDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.service.EsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Auther: 你哥
 * @Date: 2021/2/3 15:53
 * @Description:
 */
@RestController
@RequestMapping("es")
@RefreshScope //开启自动刷新配置
@Api(value = "es")
public class EsController {

    private static final Logger logger = LoggerFactory.getLogger(EsController.class);

    @Autowired
    private EsService esService;

    @ApiImplicitParam(name = "tbOrderDTO", value = "订单实体", required = true, paramType = "body",
            dataTypeClass = TbOrderDTO.class)
    @PostMapping("/addEsByOrder")
    public DataResult addEsByOrder(@RequestBody TbOrderDTO tbOrderDTO){
        try {
            esService.addEsByOrder(tbOrderDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误：" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "查询es数据")
    @GetMapping("/getEsOrderInfo/{username}")
    public DataResult<List<TbOrderVO>> getEsOrderInfo(@PathVariable("username") String username){
        try {
            List<TbOrderVO> orderVOList = esService.getEsOrderInfo(username);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(orderVOList);
        }catch (Exception e){
            logger.error("错误：" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "查询es数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "senderMobile", value = "寄件人手机号", required = true, paramType = "path",
                    dataTypeClass = String.class),
            @ApiImplicitParam(name = "orderId", value = "订单号", required = true, paramType = "path",
                    dataTypeClass = String.class)
    })

    //此方法作废不删
    @GetMapping("/getEsOrderByPhoneByOrderId/{senderMobile}/{orderId}")
    public DataResult<List<TbOrderVO>> getEsOrderByPhoneByOrderId(@PathVariable("senderMobile") String senderMobile,@PathVariable("orderId") String orderId){
        try {
            List<TbOrderVO> orderVOList = esService.getEsOrderByPhoneByOrderId(senderMobile,orderId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(orderVOList);
        }catch (Exception e){
            logger.error("错误：" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "查询es数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "senderMobile", value = "寄件人手机号", required = true, paramType = "path",
                    dataTypeClass = String.class),
            @ApiImplicitParam(name = "orderId", value = "订单号", required = true, paramType = "path",
                    dataTypeClass = String.class)
    })
    @GetMapping("/updateStatusByUsernameByOrderId")
    public DataResult updateStatusByUsernameByOrderId(@RequestParam String username,@RequestParam String orderId){
        try {
            esService.updateStatusByUsernameByOrderId(username,orderId);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("错误：" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/selectOrderEs/{phoneOrOrderId}/{orderId}/{username}")
    public DataResult<List<TbOrderVO>> selectOrderEs(@PathVariable("phoneOrOrderId") String phoneOrOrderId,@PathVariable("orderId")String orderId,@PathVariable("username") String username){
        try {
            List<TbOrderVO> orderVOList = esService.selectOrderEs(phoneOrOrderId,orderId,username);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(orderVOList);
        }catch (Exception e){
            logger.error("错误：" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


}
