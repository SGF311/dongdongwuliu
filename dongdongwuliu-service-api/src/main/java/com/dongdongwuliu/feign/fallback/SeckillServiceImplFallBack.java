package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.domain.vo.TbSeckillDiscountCouponVO;
import com.dongdongwuliu.domain.vo.TbUserDiscountCouponVO;
import com.dongdongwuliu.feign.SeckillServiceFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName SeckillServiceImplFallBack
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/1 0:36
 * @Version 1.0
 **/
@Component
public class SeckillServiceImplFallBack implements SeckillServiceFeign {
    //查询全部优惠券
    @Override
    public DataResult<List<TbSeckillDiscountCouponDTO>> selectList() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult addSeckillInfo(TbSeckillDiscountCouponVO tbSeckillDiscountCouponVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult deleteInfoById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult<List<TbSeckillDiscountCouponDTO>> selectList1() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult<TbSeckillDiscountCouponVO> selectSeckillById(Integer id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult submitStockCount(Long id, Long userId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult selectSeckillByUserIdBySumPrice(Long userId, Integer sumPrice) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult updateById(TbUserDiscountCouponVO tbUserDiscountCoupon) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }
}

 

