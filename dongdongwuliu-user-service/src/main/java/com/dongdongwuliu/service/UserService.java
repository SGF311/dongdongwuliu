package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.TbUserDTO;

public interface UserService {
    TbUserDTO getInfoByName(String username);

    void insert(TbUserDTO tbUserDTO);

    void update(TbUserDTO user);

    TbUserDTO selectById(Long id);

    TbUserDTO getInfoByPhone(String phone);
}
