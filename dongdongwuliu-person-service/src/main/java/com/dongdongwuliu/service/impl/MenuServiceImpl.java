package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdongwuliu.constant.PersonServiceConstants;
import com.dongdongwuliu.domain.dto.MenuDTO;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.mapper.MenuMapper;
import com.dongdongwuliu.mapper.RoleMenuMapper;
import com.dongdongwuliu.pojo.Menu;
import com.dongdongwuliu.pojo.RoleMenu;
import com.dongdongwuliu.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    //菜单组成
    @Override
    public List<Map<String, Object>> getMenu(TbPersonDTO person) {
        //定义一个空集合
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {

//            hashOperations.hasKey(key, field) 判断缓存中是否有我们要查找的key和field
             if (redisTemplate.opsForHash().hasKey(PersonServiceConstants.Menu_INfo_KEY, PersonServiceConstants.Menu_INfo_FIELD + person.getUid())) {
//            缓存中有数据
             resultList = (List<Map<String, Object>>) redisTemplate.opsForHash().get(PersonServiceConstants.Menu_INfo_KEY, PersonServiceConstants.Menu_INfo_FIELD + person.getUid());
              } else {
            // 说明缓存中没有数据 则去数据库中获取数据  这个sql需要5表联查 查找自己的数据
            List<Menu> menus = menuMapper.selectGetUserList(person.getUid());
            resultList = getMyTree(menus, 0);
//            将数据保存到redis中
                 redisTemplate.opsForHash().put(PersonServiceConstants.Menu_INfo_KEY, PersonServiceConstants.Menu_INfo_FIELD + person.getUid(), resultList);
                }
        } catch (Exception e) {
            throw e;
        }
        return resultList;

    }
    public List<Map<String, Object>> getMyTree(List<Menu> menus,Integer pid){
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            Map<String,Object> map = null;
            if(menus.get(i).getPid().equals(pid)){
                map = new HashMap<>();
                map.put("url",menus.get(i).getUrl());
                map.put("id",menus.get(i).getMid());
                map.put("text",menus.get(i).getText());
                map.put("nodes",getMyTree(menus ,menus.get(i).getMid()));
            }
            if(map!= null){
                List<Map<String, Object>> nodes = (List<Map<String, Object>>)map.get("nodes");
                if(nodes.size() <= 0){
                    map.remove("nodes");
                }
                list.add(map);
            }

        }
        return list;
    }


    @Override
    public List<Map<String, Object>> getZtree() {
        List<Map<String, Object>> list =  new ArrayList<>();
        try {
            List<Menu> menus = menuMapper.selectList(null);
            list =  getMyZTree(menus,0);
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    public List<Map<String, Object>> getMyZTree(List<Menu> menus,Integer pid){
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            Map<String,Object> map = null;
            if(menus.get(i).getPid().equals(pid)){
                map = new HashMap<>();
                map.put("url",menus.get(i).getUrl());
                map.put("id",menus.get(i).getMid());
                map.put("name",menus.get(i).getText());
                map.put("children",getMyZTree(menus ,menus.get(i).getMid()));
            }
            if(map!= null){
                List<Map<String, Object>> children = (List<Map<String, Object>>)map.get("children");
                if(children.size() <= 0){
                    map.remove("children");
                }
                list.add(map);
            }

        }
        return list;
    }

//增加
    @Override
    public void insertInto(MenuDTO menuDTO) {
        try {
            Menu menu = new Menu();
            BeanUtils.copyProperties(menuDTO,menu);
            menuMapper.insert(menu);
            //删除redis 根据指定用户的id删除该用户权限菜单  这里是因为前端传uid太麻烦 所以直接删除全部的key
//           redisTemplate.opsForHash().delete(PersonServiceConstants.Menu_INfo_KEY, PersonServiceConstants.Menu_INfo_FIELD + person.getUid());
            redisTemplate.delete(PersonServiceConstants.Menu_INfo_KEY);
        } catch (Exception e) {
            throw  e;
        }
    }
//根据id查找
    @Override
    public Menu getInfoById(Integer mid) {
        Menu menu = menuMapper.selectById(mid);
        return menu;
    }
//修改数据
    @Override
    public void update(MenuDTO menuDTO) {
        try {
            Menu menu = new Menu();
            BeanUtils.copyProperties(menuDTO,menu);
            menuMapper.updateById(menu);
            redisTemplate.delete(PersonServiceConstants.Menu_INfo_KEY);
        } catch (Exception e) {
            throw  e;
        }
    }
//删除
    @Override
    public void delete(Integer id) {
        try {
            menuMapper.deleteById(id);
            redisTemplate.delete(PersonServiceConstants.Menu_INfo_KEY);
        } catch (Exception e) {
            throw  e;
        }
    }
//通过角色id获取相对应的权限
    @Override
    public List<Map<String, Object>> getZtreeInfoChecked(Integer rid) {
        List<Menu> menus = menuMapper.selectList(null);


        //获取被选中的rid
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("r.rid",rid);
        List<Integer> redId = roleMenuMapper.getResIdByRid(rid);
        return getMyCheckedZTree(menus,0,redId);
    }
    public List<Map<String, Object>> getMyCheckedZTree(List<Menu> menus,Integer pid,List<Integer> redId){
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            Map<String,Object> map = null;
            if(menus.get(i).getPid().equals(pid)){
                map = new HashMap<>();
                map.put("url",menus.get(i).getUrl());
                map.put("id",menus.get(i).getMid());
                map.put("name",menus.get(i).getText());
                if(redId.contains(menus.get(i).getMid())){
                    map.put("checked",true);
                }
                map.put("children",getMyCheckedZTree(menus ,menus.get(i).getMid(),redId));
            }
            if(map!= null){
                List<Map<String, Object>> children = (List<Map<String, Object>>)map.get("children");
                if(children.size() <= 0){
                    map.remove("children");
                }
                list.add(map);
            }

        }
        return list;
    }
}
