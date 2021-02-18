package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbAreasDTO;
import com.dongdongwuliu.domain.dto.TbCitiesDTO;
import com.dongdongwuliu.domain.dto.TbProvincesDTO;
import com.dongdongwuliu.domain.vo.AddressVO;
import com.dongdongwuliu.feign.fallback.AddressServiceImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "dongdongwuliu-address-service",
        fallback = AddressServiceImplFallBack.class, url = "http://127.0.0.1:8150")
public interface AddressServiceFeign {

    //查询省
    @GetMapping("/address/getInfo")
    DataResult<List<TbProvincesDTO>> getInfo();

    //联动市
    @GetMapping("/address/getCity/{provinceId}")
    DataResult<List<TbCitiesDTO>> getCity(@PathVariable("provinceId") Integer provinceId);

    //联动区
    @GetMapping("/address/getTown/{cityid}")
    DataResult<List<TbAreasDTO>> getTown(@PathVariable("cityid")Integer cityid);

    //根据省主键查询
    @GetMapping("/address/findProvincesById/{id}")
    DataResult<TbProvincesDTO> findProvincesById(@PathVariable("id") Integer id);

    // 根据市id查询
    @GetMapping("/address/findCityById/{id}")
    DataResult<TbCitiesDTO> findCityById(@PathVariable("id") Integer id);

    // 根据县id查询
    @GetMapping("/address/findTownById/{id}")
    DataResult<TbAreasDTO> findTownById(@PathVariable("id") Integer id);

    //查询所有的市
    @GetMapping("/address/getCityAll")
    DataResult getCityAll();
    //查询所有的县
    @GetMapping("/address/getTownAll")
    DataResult getTownAll();

// ------------------------------------------------------------------------------------------

    // 查询所有用户地址
    @GetMapping("/addressController/getAddressList")
    DataResult<List<AddressVO>> getAddressList();

    // 根据用户ID查询用户地址
    @GetMapping("/addressController/getAddressByUserId/{userId}")
    DataResult<List<AddressVO>> getAddressByUserId(@PathVariable("userId")String userId);

    // 根据主键ID查询用户地址
    @GetMapping("/addressController/{id}")
    DataResult<AddressVO> getAddressById(@PathVariable("id")Long id);

    // 增加用户地址
    @PutMapping("/addressController")
    DataResult addAddress(@RequestBody AddressVO addressVO);

    // 修改用户地址
    @PutMapping("/addressController/updateAddress")
    DataResult updateAddress(@RequestBody AddressVO addressVO);

    // 根据主键ID删除
    @DeleteMapping("/addressController/{id}")
    DataResult deleteAddressById(@PathVariable("id")Long id);
}
