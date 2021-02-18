package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.dto.TbRoleDTO;
import com.dongdongwuliu.pojo.TbRole;

import java.util.List;

public interface TbRoleService {
    List<TbRole> findAll();


    void insertInTo(TbRoleDTO role, Integer[] ids);

    TbRoleDTO getInfoById(Integer rid);

    void update(TbRoleDTO role, Integer[] ids);

    void delete(Integer id);
}
