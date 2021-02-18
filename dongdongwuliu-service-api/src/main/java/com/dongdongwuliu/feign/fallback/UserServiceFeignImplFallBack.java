package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbUserDTO;
import com.dongdongwuliu.feign.UserServiceFeign;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFeignImplFallBack implements UserServiceFeign {
    @Override
    public DataResult login(String username, String password) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult registered(String username) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult register(TbUserDTO tbUserDTO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult<TbUserDTO>  getUser(String key) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }


    @Override
    public DataResult update(TbUserDTO tbUserDTO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult outLog(String key) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult<Integer> changePhone(String phone) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }
}
