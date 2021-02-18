package com.dongdongwuliu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import com.dongdongwuliu.mapper.TbPersonMapper;
import com.dongdongwuliu.pojo.TbPerson;
import com.dongdongwuliu.service.PersonRoleService;
import com.dongdongwuliu.service.TbPersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/29 21:42
 * @Version 1.0
 **/
@Service
public class TbPersonServiceImpl implements TbPersonService {

    @Resource
    private TbPersonMapper tbPersonMapper;
    @Autowired
    private PersonRoleService personRoleService;
    //登录
    //根据map查找  就是根据map的key进行条件查询  查找符合value得数据
    //map中的key对应的是数据库表中列的字段,而非类中属性名称!!!
    @Override
    public TbPersonDTO getInfoByName(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("uname", username);
        List<TbPerson> tbPersonList = (List<TbPerson>) tbPersonMapper.selectByMap(map);
        TbPersonDTO tbPersonDTO = new TbPersonDTO();
        TbPerson person =  tbPersonList == null || tbPersonList.size()== 0 ? null : tbPersonList.get(0);
        BeanUtils.copyProperties(person, tbPersonDTO);
        return tbPersonDTO;
    }
// 查询全部
    @Override
    public List<TbPerson> getInfo() {
        return tbPersonMapper.selectList(null);
    }
    //增加数据
    @Transactional
    @Override
    public void insertInto(TbPersonDTO tbPersonDTO, Integer[] roles) {
        try {
            TbPerson tbPerson = new TbPerson();
            BeanUtils.copyProperties(tbPersonDTO,tbPerson);
            //添加用户表  返回主键
            tbPersonMapper.insertReturnUid(tbPerson);
//            System.out.println("1"+tbPersonDTO.getUid());
//            System.out.println("2"+tbPerson.getUid());
            //添加数据用户角色中间表
            personRoleService.insertByUIdAndRid(tbPerson.getUid(), roles);
        }catch (Exception e){
            throw e;
        }
    }
//  根据id查询数据 并且 获取所有的角色
    @Override
    public TbPerson getInfoByUid(Integer uid) {
        TbPerson tbPerson = tbPersonMapper.selectById(uid);
        return tbPerson;
    }
//修改
    @Transactional
    @Override
    public void update(TbPersonDTO tbPersonDTO, Integer[] roles) {
        try {
            //修改用户表
            TbPerson tbPerson = new TbPerson();
            BeanUtils.copyProperties(tbPersonDTO,tbPerson);
            tbPersonMapper.updateById(tbPerson);
            //删除中间表
            personRoleService.delete(tbPerson.getUid());
            //添加数据用户角色中间表
            personRoleService.insertByUIdAndRid(tbPerson.getUid(), roles);


        } catch (Exception e) {
            throw e;
        }
    }
    //删除
    @Override
    @Transactional
    public void delete(Integer uid) {
        try {
            //shanchu用户表
            tbPersonMapper.deleteById(uid);
            //删除中间表
            personRoleService.delete(uid);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<TbPersonDTO> selectByType(Integer type,Integer sid) {
        List<TbPersonDTO> listTbPersonDTO = null;
        try {
            Map map = new HashMap();
            map.put("type",type);
            map.put("sid",sid);
            List<TbPerson> list = tbPersonMapper.selectByMap(map);
            String s = JSONObject.toJSONString(list);
            listTbPersonDTO = JSONObject.parseObject(s, List.class);
        } catch (Exception e) {
            throw e;
        }
        return listTbPersonDTO;
    }
}
