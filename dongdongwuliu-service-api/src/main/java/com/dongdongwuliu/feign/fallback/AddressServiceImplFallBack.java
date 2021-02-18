package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.AddressVO;
import com.dongdongwuliu.feign.AddressServiceFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/2/2 17:11
 * @Description:
 */
@Component
public class AddressServiceImplFallBack implements AddressServiceFeign {
    @Override
    public DataResult findProvincesById(Integer id) {
        return null;
    }

    @Override
    public DataResult findCityById(Integer id) {
        return null;
    }

    @Override
    public DataResult findTownById(Integer id) {
        return null;
    }

    @Override
    public DataResult getTown(Integer cityid) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }


    @Override
    public DataResult getCity(Integer provinceId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult getInfo() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult getCityAll() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult getTownAll() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }





    @Override
    public DataResult<List<AddressVO>> getAddressList() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult<List<AddressVO>> getAddressByUserId(String userId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult<AddressVO> getAddressById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult addAddress(AddressVO addressVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult updateAddress(AddressVO addressVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult deleteAddressById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }
}
