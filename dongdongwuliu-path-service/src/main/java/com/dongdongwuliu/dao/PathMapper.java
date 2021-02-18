package com.dongdongwuliu.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.domain.vo.TbPathVO;
import com.dongdongwuliu.pojo.TbPath;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName PathMapper
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/2 15:38
 * @Version 1.0
 **/
public interface PathMapper extends BaseMapper<TbPath> {


    @Select("select * from tb_path p ,tb_carriage_price c where p.id = c.path_id")
    List<TbPathVO> getInfo(@Param(Constants.WRAPPER) Wrapper<TbPathVO> wrapper);

    TbPath selectStartpidByInfo(@Param("startPid") Integer startPid, @Param("endPid") Integer endPid);
}

 

