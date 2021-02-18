package com.dongdongwuliu.service.impl;

import com.dongdongwuliu.constant.PersonServiceConstants;
import com.dongdongwuliu.mapper.RoleMenuMapper;
import com.dongdongwuliu.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

//增加
    @Override
    public void insertByRidAndResId(Integer rid, Integer[] ids) {
        try {
            roleMenuMapper.insertByRidAndResId(rid, ids);
        }catch (Exception e){
            throw  e;
        }
    }

    @Override
    public void deleteByRid(Integer rid) {
        Map<String,Object> map = new HashMap<>();
        map.put("rid",rid);
        roleMenuMapper.deleteByMap(map);

    }
}
