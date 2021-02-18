package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdongwuliu.domain.dto.TbCitiesDTO;
import com.dongdongwuliu.pojo.TbCities;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbCitiesMapper extends BaseMapper<TbCities> {

    @Select("select * from tb_cities where provinceid = #{provinceId}")
    List<TbCitiesDTO> getCity(Integer provinceId);
}