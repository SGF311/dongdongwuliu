package com.dongdongwuliu.service;

import com.dongdongwuliu.domain.dto.TbSpecialDTO;
import com.dongdongwuliu.pojo.TbSpecial;

import java.util.List;

public interface SpecialService {
    List<TbSpecial> getInfo();

    void addSpecial(TbSpecialDTO tbSpecialDTO);

    TbSpecial selectById(Long id);

    Boolean updateBySpecial(TbSpecial tbSpecial);

    Boolean deleteById(Long id);
}
