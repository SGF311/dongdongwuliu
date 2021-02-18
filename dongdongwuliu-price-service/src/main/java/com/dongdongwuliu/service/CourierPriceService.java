package com.dongdongwuliu.service;

import com.dongdongwuliu.pojo.CourierPrice;

import java.util.List;

public interface CourierPriceService {
    List<CourierPrice> getCourierPriceList();

    List<CourierPrice> getCourierPriceBySiteId(Integer siteId);

    CourierPrice getCourierPriceBySiteIdAndSiteScope(CourierPrice courierPrice);

    CourierPrice getCourierPriceById(Long id);

    Boolean addCourierPrice(CourierPrice courierPrice);

    Boolean updateCourierPrice(CourierPrice courierPrice);

    Boolean deleteCourierPriceById(Long id);

    Boolean deleteCourierPriceBySiteId(Integer siteId);
}
