package com.dongdongwuliu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.pojo.TbCar;

import java.util.List;
import java.util.Map;

public interface TbCarService {

    TbCarDTO selectByIdOrCarNumber(TbCarDTO tbCarDTO);

    void insertInfoCar(TbCarDTO tbCarDTO);

    void deleteByIdOrCarNumber(TbCarDTO tbCarDTO);

    void updateByIdOrCarNumber(TbCarDTO tbCarDTO);

    Map<String, Object> selectPage(TbCarDTO tbCarDTO,Integer pageNumber, Integer pageSize);

    List<TbCar> carControlSelect(Integer status,Integer siteId);

    List<TbCarDTO> selectBySiteId(TbCarDTO tbCarDTO);
}
