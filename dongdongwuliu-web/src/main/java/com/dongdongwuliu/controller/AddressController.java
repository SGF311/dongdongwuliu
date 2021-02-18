package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.AddressVO;
import com.dongdongwuliu.feign.AddressServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("userAddress")
public class AddressController {
    @Resource
    private AddressServiceFeign addressServiceFeign;

    private Logger logger = LoggerFactory.getLogger(AddressController.class);

    // 根据用户ID查询用户地址
    @RequestMapping("getAddressByUserId")
    @ResponseBody
    public DataResult getAddressByUserId(String userId){
        try {
            DataResult<List<AddressVO>> dataResult = addressServiceFeign.getAddressByUserId(userId);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 增加用户地址
    @RequestMapping("addAddress")
    @ResponseBody
    public DataResult addAddress(AddressVO addressVO){
        try {
            DataResult dataResult = addressServiceFeign.addAddress(addressVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 修改用户地址
    @RequestMapping("updateAddress")
    @ResponseBody
    public DataResult updateAddress(AddressVO addressVO){
        try {
            DataResult dataResult = addressServiceFeign.updateAddress(addressVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据主键ID删除
    @RequestMapping("deleteAddressById")
    @ResponseBody
    public DataResult deleteAddressById(Long id){
        try {
            DataResult dataResult = addressServiceFeign.deleteAddressById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据主键ID查询用户地址
    @RequestMapping("getAddressById")
    @ResponseBody
    public DataResult getAddressById(Long id){
        try {
            DataResult dataResult = addressServiceFeign.getAddressById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
