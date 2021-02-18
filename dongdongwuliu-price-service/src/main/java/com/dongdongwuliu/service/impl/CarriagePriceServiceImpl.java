package com.dongdongwuliu.service.impl;

import com.dongdongwuliu.mapper.CarriagePriceMapper;
import com.dongdongwuliu.pojo.TbCarriagePrice;
import com.dongdongwuliu.service.CarriagePriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarriagePriceServiceImpl implements CarriagePriceService {
    @Resource
    private CarriagePriceMapper carriagePriceMapper;

    // 查询所有普通线路运输价格
    @Override
    public List<TbCarriagePrice> getCarriagePriceList() {
        return carriagePriceMapper.selectList(null);
    }

    // 根据普通线路ID查询运输价格
    @Override
    public TbCarriagePrice getCarriagePriceByPathId(Long pathId) {
        Map<String, Object> map = new HashMap<>();
        map.put("path_id", pathId);
        List<TbCarriagePrice> carriagePrices = carriagePriceMapper.selectByMap(map);
        if (carriagePrices.isEmpty()){
            return null;
        }
        return carriagePrices.get(0);
    }

    // 根据普通线路价格ID查询运输价格
    @Override
    public TbCarriagePrice getCarriagePriceById(Long id) {
        return carriagePriceMapper.selectById(id);
    }

    // 增加普通线路运输价格
    @Transactional
    @Override
    public Boolean addCarriagePrice(TbCarriagePrice carriagePrice) {
        int num = carriagePriceMapper.insert(carriagePrice);
        return num >= 1;
    }

    // 修改普通线路运输价格
    @Transactional
    @Override
    public Boolean updateCarriagePrice(TbCarriagePrice carriagePrice) {
        int num = carriagePriceMapper.updateById(carriagePrice);
        return num >= 1;
    }

    // 根据普通线路价格ID删除
    @Transactional
    @Override
    public Boolean deleteCarriagePriceById(Long id) {
        int num = carriagePriceMapper.deleteById(id);
        return num >= 1;
    }

    // 根据普通线路ID删除
    @Transactional
    @Override
    public Boolean deleteCarriagePriceByPathId(Long pathId) {
        Map<String, Object> map = new HashMap<>();
        map.put("path_id", pathId);
        int num = carriagePriceMapper.deleteByMap(map);
        return num >= 1;
    }
}
