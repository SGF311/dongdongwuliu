package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdongwuliu.domain.dto.TbCargoTypeDTO;
import com.dongdongwuliu.pojo.TbCargoType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbCargoTypeMapper extends BaseMapper<TbCargoType> {

    @Select("select * from tb_cargo_type")
    List<TbCargoTypeDTO> getCargoTypeInfo(@Param(Constants.WRAPPER) Wrapper<TbCargoTypeDTO> wrapper);
}