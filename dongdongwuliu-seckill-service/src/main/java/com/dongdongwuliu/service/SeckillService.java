package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.TbSeckillDiscountCouponDTO;
import com.dongdongwuliu.domain.vo.TbUserDiscountCouponVO;
import com.dongdongwuliu.pojo.TbSeckillDiscountCoupon;
import com.dongdongwuliu.pojo.TbUserDiscountCoupon;

import java.util.List;

public interface SeckillService {
    List<TbSeckillDiscountCouponDTO> selectList();

    List<TbSeckillDiscountCouponDTO> selectList1();

    Integer addSeckillInfo(TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO);

    TbSeckillDiscountCouponDTO selectSeckillById(Integer id);

    Integer updateSeckillByInfo(TbSeckillDiscountCouponDTO tbSeckillDiscountCouponDTO);

    Integer deleteInfoById(Long id);

    void submitStockCount(Long id, Long userId);

    List<TbUserDiscountCoupon> selectSeckillByUserIdBySumPrice(Long userId, Integer sumPrice);

    void updateById(TbUserDiscountCouponVO tbUserDiscountCoupon);
}
