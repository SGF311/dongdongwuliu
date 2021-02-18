package com.dongdongwuliu.Service;

import com.dongdongwuliu.domain.dto.TbContentDTO;


import java.util.List;
import java.util.Map;

public interface TbContentService {
    Map<String, Object> pageInfo(Integer pageNum, Integer pageSize);

    void insert(TbContentDTO tbContent);

    void deleteById(Long id);

    void deleteBatch(Long[] ids);

    TbContentDTO toUpdate(Long id);

    void update(TbContentDTO tbContentDTO);

    void upDown(Long id, Integer flag);

    List<TbContentDTO> getContentByContentCategoryId(Long contentCategoryId);
}
