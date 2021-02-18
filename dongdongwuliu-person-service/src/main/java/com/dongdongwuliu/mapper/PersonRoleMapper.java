package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongdongwuliu.pojo.PersonRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonRoleMapper extends BaseMapper<PersonRole> {

    void insertByUIdAndRid(@Param("pid") Integer pid, @Param("role")Integer[] role);

    List<Integer> selectByPidReturnRid(@Param("pid")Integer pid);
}
