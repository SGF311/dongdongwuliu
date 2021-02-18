package com.dongdongwuliu.service.impl;


import com.dongdongwuliu.constant.PersonServiceConstants;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.dto.TbRoleDTO;
import com.dongdongwuliu.mapper.TbRoleMapper;
import com.dongdongwuliu.pojo.TbRole;
import com.dongdongwuliu.service.RoleMenuService;
import com.dongdongwuliu.service.TbRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbRoleServiceImpl implements TbRoleService {
    @Resource
    private TbRoleMapper tbRoleMapper;
    @Resource
    private RoleMenuService roleMenuService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<TbRole> findAll() {

        try {
            List<TbRole> tbRoles = tbRoleMapper.selectList(null);
            return tbRoles;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    @Override
    public void insertInTo(TbRoleDTO role, Integer[] ids) {
            //增加角色 返回id
            TbRole tbRole = new TbRole();
            BeanUtils.copyProperties(role,tbRole);
            tbRoleMapper.insertReturnRid(tbRole);

            //增加到中间表
            roleMenuService.insertByRidAndResId(tbRole.getRid(),ids);
        //删除redis 根据指定用户的id删除该用户权限菜单  这里是因为前端传uid太麻烦 所以直接删除全部的key
//           redisTemplate.opsForHash().delete(PersonServiceConstants.Menu_INfo_KEY, PersonServiceConstants.Menu_INfo_FIELD + person.getUid());
        redisTemplate.delete(PersonServiceConstants.Menu_INfo_KEY);

    }

    @Override
    public TbRoleDTO getInfoById(Integer rid) {
        TbRole tbRole = tbRoleMapper.selectById(rid);
        TbRoleDTO tbRoleDTO = new TbRoleDTO();
        BeanUtils.copyProperties(tbRole,tbRoleDTO);
        return tbRoleDTO;
    }

    @Transactional
    @Override
    public void update(TbRoleDTO role, Integer[] ids) {
        try {
            //增加角色 返回id
            TbRole tbRole = new TbRole();
            BeanUtils.copyProperties(role,tbRole);
            tbRoleMapper.updateById(tbRole);
            //删除中间表
            roleMenuService.deleteByRid(role.getRid());
            //增加到中间表
            roleMenuService.insertByRidAndResId(tbRole.getRid(),ids);
            //删除redis 根据指定用户的id删除该用户权限菜单  这里是因为前端传uid太麻烦 所以直接删除全部的key
//           redisTemplate.opsForHash().delete(PersonServiceConstants.Menu_INfo_KEY, PersonServiceConstants.Menu_INfo_FIELD + person.getUid());
            redisTemplate.delete(PersonServiceConstants.Menu_INfo_KEY);
        }catch (Exception e){
            throw  e;
        }
    }
    @Transactional
    @Override
    public void delete(Integer id) {
        tbRoleMapper.deleteById(id);
        roleMenuService.deleteByRid(id);
        //删除redis 根据指定用户的id删除该用户权限菜单  这里是因为前端传uid太麻烦 所以直接删除全部的key
//           redisTemplate.opsForHash().delete(PersonServiceConstants.Menu_INfo_KEY, PersonServiceConstants.Menu_INfo_FIELD + person.getUid());
        redisTemplate.delete(PersonServiceConstants.Menu_INfo_KEY);
    }
}
