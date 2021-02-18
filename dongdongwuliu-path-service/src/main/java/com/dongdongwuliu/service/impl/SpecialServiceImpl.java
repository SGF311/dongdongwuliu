package com.dongdongwuliu.service.impl;

import com.dongdongwuliu.dao.SpecialMapper;
import com.dongdongwuliu.domain.dto.TbSpecialDTO;
import com.dongdongwuliu.pojo.TbSpecial;
import com.dongdongwuliu.service.SpecialService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SpecialServiceImpl
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/3 17:13
 * @Version 1.0
 **/
@Service
public class SpecialServiceImpl implements SpecialService {

    @Resource
    private SpecialMapper specialMapper;

    //查询
    @Override
    public List<TbSpecial> getInfo() {
        List<TbSpecial> list = specialMapper.selectList(null);
        return list;
    }

    //增加
    @Override
    @Transactional
    public void addSpecial(TbSpecialDTO tbSpecialDTO) {
        TbSpecial tbSpecial = new TbSpecial();
        BeanUtils.copyProperties(tbSpecialDTO,tbSpecial);
        specialMapper.insert(tbSpecial);
    }

    //回显
    @Override
    public TbSpecial selectById(Long id) {
        TbSpecial tbSpecial = specialMapper.selectById(id);
        return tbSpecial;
    }

    //修改
    @Override
    @Transactional
    public Boolean updateBySpecial(TbSpecial tbSpecial) {
        int i = specialMapper.updateById(tbSpecial);
        return i > 0;
    }

    //删除
    @Transactional
    @Override
    public Boolean deleteById(Long id) {
        int i = specialMapper.deleteById(id);
        return i>0;
    }
}

 

