package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.AddressVO;
import com.dongdongwuliu.pojo.TbAddress;
import com.dongdongwuliu.service.AddressService;
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
@RequestMapping("addressController")
@RefreshScope   // 开启自动刷新配置
@Api(description = "用户地址管理")
public class TbAddressController {
    private Logger logger = LoggerFactory.getLogger(TbAddressController.class);

    @Autowired
    private AddressService addressService;

    @GetMapping("getAddressList")
    @ApiOperation("查询所有用户地址")
    public DataResult getAddressList(){
        try {
            List<AddressVO> addressList = addressService.getAddressList();
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (addressList.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(addressList);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAddressByUserId/{userId}")
    @ApiOperation("根据用户ID查询用户地址")
    @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "path")
    public DataResult getAddressByUserId(@PathVariable("userId")String userId){
        try {
            List<AddressVO> addressList = addressService.getAddressByUserId(userId);
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (addressList.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            String jsonString = JSONObject.toJSONString(addressList);
            List<AddressVO> addressVOList = JSONObject.parseObject(jsonString, List.class);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(addressVOList);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("根据主键ID查询用户地址")
    @ApiImplicitParam(name = "id", value = "主键ID", paramType = "path")
    public DataResult getAddressById(@PathVariable("id")Long id){
        try {
            AddressVO address = addressService.getAddressById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(address);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @ApiOperation("增加用户地址")
    @ApiImplicitParam(name = "addressVO", value = "用户地址实体", paramType = "body",
            required = true, dataTypeClass = AddressVO.class)
    public DataResult addAddress(@RequestBody AddressVO addressVO){
        try {
            TbAddress address = new TbAddress();
            BeanUtils.copyProperties(addressVO, address);
            Boolean b = addressService.addAddress(address);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateAddress")
    @ApiOperation("修改用户地址")
    @ApiImplicitParam(name = "addressVO", value = "用户地址实体", paramType = "body",
            required = true, dataTypeClass = AddressVO.class)
    public DataResult updateAddress(@RequestBody AddressVO addressVO){
        try {
            TbAddress address = new TbAddress();
            BeanUtils.copyProperties(addressVO, address);
            Boolean b = addressService.updateAddress(address);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据主键ID删除")
    @ApiImplicitParam(name = "id", value = "主键ID", paramType = "path")
    public DataResult deleteAddressById(@PathVariable("id")Long id){
        try {
            Boolean b = addressService.deleteAddressById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
