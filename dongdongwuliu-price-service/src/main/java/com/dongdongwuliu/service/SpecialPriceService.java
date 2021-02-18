package com.dongdongwuliu.service;

import com.dongdongwuliu.pojo.TbSpecialPrice;

import java.util.List;

public interface SpecialPriceService {
    List<TbSpecialPrice> getSpecialPriceList();

    TbSpecialPrice getSpecialPriceBySpecialId(Long specialId);

    TbSpecialPrice getSpecialPriceById(Long id);

    Boolean addSpecialPrice(TbSpecialPrice specialPrice);

    Boolean updateSpecialPrice(TbSpecialPrice specialPrice);

    Boolean deleteSpecialPriceById(Long id);

    Boolean deleteSpecialPriceBySpecialId(Long specialId);
}
