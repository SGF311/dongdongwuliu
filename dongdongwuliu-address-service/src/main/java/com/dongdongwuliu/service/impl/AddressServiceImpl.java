package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdongwuliu.domain.vo.AddressVO;
import com.dongdongwuliu.mapper.AddressMapper;
import com.dongdongwuliu.pojo.TbAddress;
import com.dongdongwuliu.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    // 查询所有用户地址
    @Override
    public List<AddressVO> getAddressList() {
        return addressMapper.selectAll();
    }

    // 根据用户ID查询用户地址
    @Override
    public List<AddressVO> getAddressByUserId(String userId) {
        QueryWrapper<AddressVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ad.user_id",userId);
        return addressMapper.getAddressByUserId(queryWrapper);
    }

    // 根据主键ID查询用户地址
    @Override
    public AddressVO getAddressById(Long id) {
        QueryWrapper<AddressVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ad.id",id);
        return addressMapper.getAddressById(queryWrapper);
    }

    // 增加用户地址
    @Transactional
    @Override
    public Boolean addAddress(TbAddress address) {
        address.setCreateDate(new Date());
        int num = addressMapper.insert(address);
        return num >= 1;
    }

    // 修改用户地址
    @Transactional
    @Override
    public Boolean updateAddress(TbAddress address) {
        int num = addressMapper.updateById(address);
        return num >= 1;
    }

    // 根据主键ID删除
    @Transactional
    @Override
    public Boolean deleteAddressById(Long id) {
        int num = addressMapper.deleteById(id);
        return num >= 1;
    }
}
