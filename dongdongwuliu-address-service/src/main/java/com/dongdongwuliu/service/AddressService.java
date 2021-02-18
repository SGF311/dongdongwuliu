package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.vo.AddressVO;
import com.dongdongwuliu.pojo.TbAddress;

import java.util.List;

public interface AddressService {
    List<AddressVO> getAddressList();

    List<AddressVO> getAddressByUserId(String userId);

    AddressVO getAddressById(Long id);

    Boolean addAddress(TbAddress address);

    Boolean updateAddress(TbAddress address);

    Boolean deleteAddressById(Long id);
}
