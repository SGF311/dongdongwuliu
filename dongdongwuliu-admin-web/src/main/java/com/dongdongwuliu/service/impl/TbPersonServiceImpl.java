package com.dongdongwuliu.service.impl;

import com.dongdongwuliu.mapper.TbPersonMapper;
import com.dongdongwuliu.pojo.TbPerson;
import com.dongdongwuliu.service.TbPersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/29 21:42
 * @Version 1.0
 **/
@Service
public class TbPersonServiceImpl implements TbPersonService {
    @Resource
    private TbPersonMapper tUserMapper;


    @Override
    public TbPerson selectById(Integer id) {
        return tUserMapper.selectById(id);
    }
}
