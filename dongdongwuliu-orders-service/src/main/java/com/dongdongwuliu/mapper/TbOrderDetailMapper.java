package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdongwuliu.domain.dto.TbOrderDetailDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.pojo.TbOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbOrderDetailMapper extends BaseMapper<TbOrderDetail> {

    //${ew.customSqlSegment}   MP会自动识别并作为where语句加入到自定义SQL语句中
    @Select("select * from tb_order_detail ${ew.customSqlSegment}")
    List<TbOrderDetail> selectVisitTimeGtTime(@Param(Constants.WRAPPER) Wrapper<TbOrderDetail> wrapper);
}