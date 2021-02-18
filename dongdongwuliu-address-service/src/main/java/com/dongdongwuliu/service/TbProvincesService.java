package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.TbAreasDTO;
import com.dongdongwuliu.domain.dto.TbCitiesDTO;
import com.dongdongwuliu.pojo.TbAreas;
import com.dongdongwuliu.pojo.TbCities;
import com.dongdongwuliu.pojo.TbProvinces;

import java.util.List;

public interface TbProvincesService {
    List<TbProvinces> getInfo();

    List<TbCitiesDTO> getCity(Integer provinceId);

    List<TbAreasDTO> getTown(Integer cityid);

    List<TbCities> getCityAll();

    List<TbAreas> getTownAll();

    TbProvinces findProvincesById(Integer id);

    TbAreas findTownById(Integer id);

    TbCities findCityById(Integer id);
}
