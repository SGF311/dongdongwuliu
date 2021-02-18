package com.dongdongwuliu.service.impl;

import com.dongdongwuliu.config.IdWorker;
import com.dongdongwuliu.domain.dto.TbUserDTO;
import com.dongdongwuliu.domain.vo.TbUserVO;
import com.dongdongwuliu.mapper.TbUserMapper;
import com.dongdongwuliu.pojo.TbUser;
import com.dongdongwuliu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceImpl implements UserService {
    @Resource
    private TbUserMapper tbUserMapper;
    @Resource
    private IdWorker idWorker;

    @Override
    public TbUserDTO getInfoByName(String username) {
        Map map = new HashMap();
        map.put("username",username);
        List<TbUser> list = tbUserMapper.selectByMap(map);
        TbUser person =  list == null || list.size()== 0 ? new TbUser() : list.get(0);
        TbUserDTO tbUserDTO = new TbUserDTO();
        BeanUtils.copyProperties(person,tbUserDTO);
        return tbUserDTO;
    }

    @Override
    public void insert(TbUserDTO tbUserDTO) {

        try {
            TbUser tbUser = new TbUser();
            BeanUtils.copyProperties(tbUserDTO,tbUser);
            tbUser.setCreated(new Date());
            tbUser.setId(idWorker.nextId());
            tbUser.setUpdated(new Date());

            tbUserMapper.insert(tbUser);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void update(TbUserDTO user) {
        try {
            TbUser tbUser = new TbUser();
            BeanUtils.copyProperties(user,tbUser);
            tbUser.setUpdated(new Date());
            tbUserMapper.updateById(tbUser);
        } catch (BeansException e) {
            throw e;
        }

    }

    @Override
    public TbUserDTO selectById(Long id) {
        try {
            TbUserDTO tbUserDTO = new TbUserDTO();
            TbUser tbUser = tbUserMapper.selectById(id);
            BeanUtils.copyProperties(tbUser,tbUserDTO);
            return tbUserDTO;
        } catch (BeansException e) {
            throw e;
        }
    }

    @Override
    public TbUserDTO getInfoByPhone(String phone) {
        Map map = new HashMap();
        map.put("phone",phone);
        List<TbUser> list = tbUserMapper.selectByMap(map);
        TbUser person =  list == null || list.size()== 0 ? new TbUser() : list.get(0);
        TbUserDTO tbUserDTO = new TbUserDTO();
        BeanUtils.copyProperties(person,tbUserDTO);
        return tbUserDTO;
    }
}
