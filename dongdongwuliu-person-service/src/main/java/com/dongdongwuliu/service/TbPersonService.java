package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.pojo.TbPerson;

import java.util.List;

public interface TbPersonService {


    TbPersonDTO getInfoByName(String username);

    List<TbPerson> getInfo();


    void insertInto(TbPersonDTO tbPersonDTO, Integer[] roles);

    TbPerson getInfoByUid(Integer uid);

    void update(TbPersonDTO tbPersonDTO, Integer[] roles);

    void delete(Integer uid);

    List<TbPersonDTO> selectByType(Integer type,Integer sid);
}
