package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.feign.TbCarServiceFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/2/1 12:32
 * @Version 1.0
 **/
@Component
public class TbCarServiceImplFallBacK implements TbCarServiceFeign {
    @Override
    public DataResult<List<TbCarDTO>> selectPage(TbCarDTO tbCarDTO,Integer pageNumber, Integer pageSize) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,展示所有及分页");
    }

    @Override
    public DataResult<TbCarVO> selectByIdOrCarNumber(TbCarDTO tbCarDTO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,查询失败");
    }

    @Override
    public DataResult insertInfoCar(TbCarVO tbCarVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,添加失败");
    }

    @Override
    public DataResult updateByIdOrCarNumber(TbCarVO carVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,添加失败");
    }

    @Override
    public DataResult deleteByIdOrCarNumber(TbCarVO carVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,删除失败");
    }

    @Override
    public DataResult carControlSelect() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务熔断,网络太差,查询失败");
    }
}