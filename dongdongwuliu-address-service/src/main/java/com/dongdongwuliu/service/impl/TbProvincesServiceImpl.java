package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdongwuliu.domain.dto.TbAreasDTO;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.domain.dto.TbCitiesDTO;
import com.dongdongwuliu.mapper.TbAreasMapper;
import com.dongdongwuliu.mapper.TbCitiesMapper;
import com.dongdongwuliu.mapper.TbProvincesMapper;
import com.dongdongwuliu.pojo.TbAreas;
import com.dongdongwuliu.pojo.TbCities;
import com.dongdongwuliu.pojo.TbProvinces;
import com.dongdongwuliu.service.TbProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/2/2 18:00
 * @Description:
 */
@Component
public class TbProvincesServiceImpl implements TbProvincesService {

    @Resource
    private TbProvincesMapper tbProvincesMapper;

    @Resource
    private TbCitiesMapper tbCitiesMapper;

    @Resource
    private TbAreasMapper tbAreasMapper;

    @Override
    public TbAreas findTownById(Integer id) {
        return tbAreasMapper.selectById(id);
    }

    @Override
    public TbCities findCityById(Integer id) {
        return tbCitiesMapper.selectById(id);
    }

    @Override
    public TbProvinces findProvincesById(Integer id) {
        return tbProvincesMapper.selectById(id);
    }

    @Override
    public List<TbAreas> getTownAll() {
        QueryWrapper<TbAreas> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        return tbAreasMapper.selectList(queryWrapper);
    }

    @Override
    public List<TbCities> getCityAll() {
        QueryWrapper<TbCities> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        return tbCitiesMapper.selectList(queryWrapper);
    }

    @Override
    public List<TbAreasDTO> getTown(Integer cityid) {
        return tbAreasMapper.getCity(cityid);
    }

    @Override
    public List<TbCitiesDTO> getCity(Integer provinceId) {
        return tbCitiesMapper.getCity(provinceId);
    }

    @Override
    public List<TbProvinces> getInfo() {
        QueryWrapper<TbProvinces> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        return tbProvincesMapper.selectList(queryWrapper);
    }
}
