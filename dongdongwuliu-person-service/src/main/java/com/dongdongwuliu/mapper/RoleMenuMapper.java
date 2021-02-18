package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongdongwuliu.pojo.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper  extends BaseMapper<RoleMenu> {

    List<Integer> getResIdByRid(@Param("rid") Integer rid);

    void insertByRidAndResId(@Param("rid") Integer rid, @Param("ids") Integer[] ids);


}
