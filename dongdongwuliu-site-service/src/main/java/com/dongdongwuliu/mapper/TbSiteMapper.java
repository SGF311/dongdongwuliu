package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdongwuliu.pojo.TbSite;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TbSiteMapper extends BaseMapper<TbSite> {

    //页面显示时对应汉字 省市县
    List<TbSite> selectAll(@Param(Constants.WRAPPER) Wrapper<TbSite> wrapper);
}
