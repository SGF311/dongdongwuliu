package com.dongdongwuliu.Service;

import com.dongdongwuliu.domain.dto.TbContentCategoryDTO;

import java.util.List;

public interface TbContentCategoryService {
    List<TbContentCategoryDTO> findAll();

    void insert(TbContentCategoryDTO tbContentCategoryDTO);

    void deleteById(Long id);

    void deleteBatch(Long[] ids);

    TbContentCategoryDTO toUpdate(Long id);

    void update(TbContentCategoryDTO tbContentCategoryDTO);
}
