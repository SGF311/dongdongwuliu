package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongdongwuliu.pojo.Menu;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectGetUserList(@Param("uid") Integer uid);
}
