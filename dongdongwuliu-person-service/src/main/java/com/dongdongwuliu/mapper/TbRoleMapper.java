package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongdongwuliu.pojo.TbRole;

public interface TbRoleMapper extends BaseMapper<TbRole> {

    void insertReturnRid(TbRole tbRole);
}
