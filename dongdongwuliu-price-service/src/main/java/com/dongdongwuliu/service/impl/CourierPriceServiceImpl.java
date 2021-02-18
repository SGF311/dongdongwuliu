package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdongwuliu.mapper.CourierPriceMapper;
import com.dongdongwuliu.pojo.CourierPrice;
import com.dongdongwuliu.service.CourierPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourierPriceServiceImpl implements CourierPriceService {
    @Resource
    private CourierPriceMapper courierPriceMapper;

    // 查询所有配送价格
    @Override
    public List<CourierPrice> getCourierPriceList() {
        return courierPriceMapper.selectList(null);
    }

    // 根据站点ID查询配送价格
    @Override
    public List<CourierPrice> getCourierPriceBySiteId(Integer siteId) {
        Map<String, Object> map = new HashMap<>();
        map.put("site_id", siteId);
        return courierPriceMapper.selectByMap(map);
    }

    // 根据站点ID以及配送距离查询配送价格
    @Override
    public CourierPrice getCourierPriceBySiteIdAndSiteScope(CourierPrice courierPrice) {
        QueryWrapper<CourierPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("site_id",courierPrice.getSiteId()).
                le("site_scope",courierPrice.getSiteScope()).
                orderByDesc("site_scope");
        List<CourierPrice> courierPriceList = courierPriceMapper.selectList(queryWrapper);
        // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
        if (courierPriceList.isEmpty()){
            return null;
        }
        return courierPriceList.get(0);
    }

    // 根据ID查询配送价格
    @Override
    public CourierPrice getCourierPriceById(Long id) {
        return courierPriceMapper.selectById(id);
    }

    // 增加配送价格
    @Transactional
    @Override
    public Boolean addCourierPrice(CourierPrice courierPrice) {
        int num = courierPriceMapper.insert(courierPrice);
        return num >= 1;
    }

    // 根据ID修改配送价格
    @Transactional
    @Override
    public Boolean updateCourierPrice(CourierPrice courierPrice) {
        int num = courierPriceMapper.updateById(courierPrice);
        return num >= 1;
    }

    // 根据ID删除
    @Transactional
    @Override
    public Boolean deleteCourierPriceById(Long id) {
        int num = courierPriceMapper.deleteById(id);
        return num >= 1;
    }

    // 根据站点ID删除
    @Transactional
    @Override
    public Boolean deleteCourierPriceBySiteId(Integer siteId) {
        Map<String, Object> map = new HashMap<>();
        map.put("site_id", siteId);
        int num = courierPriceMapper.deleteByMap(map);
        return num >= 1;
    }
}
