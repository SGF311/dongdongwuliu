package com.dongdongwuliu.service;

import com.dongdongwuliu.pojo.TbCarriagePrice;

import java.util.List;

public interface CarriagePriceService {
    List<TbCarriagePrice> getCarriagePriceList();

    TbCarriagePrice getCarriagePriceByPathId(Long pathId);

    TbCarriagePrice getCarriagePriceById(Long id);

    Boolean addCarriagePrice(TbCarriagePrice carriagePrice);

    Boolean updateCarriagePrice(TbCarriagePrice carriagePrice);

    Boolean deleteCarriagePriceById(Long id);

    Boolean deleteCarriagePriceByPathId(Long pathId);
}
