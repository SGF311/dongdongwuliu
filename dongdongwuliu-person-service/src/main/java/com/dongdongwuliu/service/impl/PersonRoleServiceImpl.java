package com.dongdongwuliu.service.impl;

import com.dongdongwuliu.mapper.PersonRoleMapper;
import com.dongdongwuliu.service.PersonRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonRoleServiceImpl implements PersonRoleService {
    @Resource
    private PersonRoleMapper personRoleMapper;

    @Override
    public List<Integer> getInFoByUidGetRid(Integer pid) {

        List<Integer> list = personRoleMapper.selectByPidReturnRid(pid);

        return list;
    }
//删除
    @Override
    public void delete(Integer uid) {
        Map<String,Object> map = new HashMap<>();
        map.put("pid",uid);
        personRoleMapper.deleteByMap(map);
    }
//新增
    @Override
    public void insertByUIdAndRid(Integer uid, Integer[] roles) {
        personRoleMapper.insertByUIdAndRid(uid,roles);
    }
}
