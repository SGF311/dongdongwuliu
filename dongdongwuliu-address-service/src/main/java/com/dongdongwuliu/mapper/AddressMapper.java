package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdongwuliu.domain.vo.AddressVO;
import com.dongdongwuliu.pojo.TbAddress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddressMapper extends BaseMapper<TbAddress> {

    @Select("select * from tb_address ad, tb_provinces pr, tb_cities ci, tb_areas ar\n" +
            "\twhere ad.province_id = pr.provinceid and ad.city_id = ci.cityid and ad.town_id = ar.areaid")
    List<AddressVO> selectAll();

    @Select("select * from tb_address ad, tb_provinces pr, tb_cities ci, tb_areas ar\n" +
            "\t${ew.customSqlSegment} and ad.province_id = pr.provinceid and ad.city_id = ci.cityid and ad.town_id = ar.areaid" )
    List<AddressVO> getAddressByUserId(@Param(Constants.WRAPPER) Wrapper<AddressVO> wrapper);

    @Select("select * from tb_address ad, tb_provinces pr, tb_cities ci, tb_areas ar\n" +
            "\t${ew.customSqlSegment} and ad.province_id = pr.provinceid and ad.city_id = ci.cityid and ad.town_id = ar.areaid" )
    AddressVO getAddressById(@Param(Constants.WRAPPER) Wrapper<AddressVO> wrapper);
}
