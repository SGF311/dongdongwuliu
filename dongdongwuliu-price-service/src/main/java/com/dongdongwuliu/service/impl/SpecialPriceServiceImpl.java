package com.dongdongwuliu.service.impl;

import com.dongdongwuliu.mapper.SpecialPriceMapper;
import com.dongdongwuliu.pojo.TbSpecialPrice;
import com.dongdongwuliu.service.SpecialPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecialPriceServiceImpl implements SpecialPriceService {
    @Resource
    private SpecialPriceMapper specialPriceMapper;

    // 查询所有专线线路运输价格
    @Override
    public List<TbSpecialPrice> getSpecialPriceList() {
        return specialPriceMapper.selectList(null);
    }

    // 根据专线线路ID查询运输价格
    @Override
    public TbSpecialPrice getSpecialPriceBySpecialId(Long specialId) {
        Map<String, Object> map = new HashMap<>();
        map.put("special_id", specialId);
        List<TbSpecialPrice> specialPriceList = specialPriceMapper.selectByMap(map);
        if (specialPriceList.isEmpty()){
            return null;
        }
        return specialPriceList.get(0);
    }

    // 根据专线线路价格ID查询运输价格
    @Override
    public TbSpecialPrice getSpecialPriceById(Long id) {
        return specialPriceMapper.selectById(id);
    }

    // 增加专线线路运输价格
    @Transactional
    @Override
    public Boolean addSpecialPrice(TbSpecialPrice specialPrice) {
        int num = specialPriceMapper.insert(specialPrice);
        return num >= 1;
    }

    // 修改专线线路运输价格
    @Transactional
    @Override
    public Boolean updateSpecialPrice(TbSpecialPrice specialPrice) {
        int num = specialPriceMapper.updateById(specialPrice);
        return num >= 1;
    }

    // 根据专线价格ID删除
    @Transactional
    @Override
    public Boolean deleteSpecialPriceById(Long id) {
        int num = specialPriceMapper.deleteById(id);
        return num >= 1;
    }

    // 根据专线线路ID删除
    @Transactional
    @Override
    public Boolean deleteSpecialPriceBySpecialId(Long specialId) {
        Map<String, Object> map = new HashMap<>();
        map.put("special_id", specialId);
        int num = specialPriceMapper.deleteByMap(map);
        return num >= 1;
    }
}
