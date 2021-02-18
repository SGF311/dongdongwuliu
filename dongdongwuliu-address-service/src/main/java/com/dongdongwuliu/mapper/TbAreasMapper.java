package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdongwuliu.domain.dto.TbAreasDTO;
import com.dongdongwuliu.pojo.TbAreas;
import com.dongdongwuliu.pojo.TbCities;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbAreasMapper extends BaseMapper<TbAreas> {

    @Select("select * from tb_areas where cityid = #{cityid}")
    List<TbAreasDTO> getCity(Integer cityid);
}