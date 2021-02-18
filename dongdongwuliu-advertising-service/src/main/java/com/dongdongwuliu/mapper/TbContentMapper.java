package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongdongwuliu.pojo.TbContent;
import org.springframework.data.repository.query.Param;

public interface TbContentMapper extends BaseMapper<TbContent> {


    TbContent selectByUp(@Param("sortOrder") Integer sortOrder);

    TbContent selectByDown(@Param("sortOrder")Integer sortOrder);
}
