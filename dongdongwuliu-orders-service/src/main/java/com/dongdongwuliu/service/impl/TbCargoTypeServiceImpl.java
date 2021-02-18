package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdongwuliu.domain.dto.TbCargoTypeDTO;
import com.dongdongwuliu.domain.dto.TbCourierDTO;
import com.dongdongwuliu.domain.dto.TbOrderDetailDTO;
import com.dongdongwuliu.mapper.TbCargoTypeMapper;
import com.dongdongwuliu.mapper.TbCourierMapper;
import com.dongdongwuliu.mapper.TbOrderDetailMapper;
import com.dongdongwuliu.pojo.TbCourier;
import com.dongdongwuliu.pojo.TbOrderDetail;
import com.dongdongwuliu.service.TbCargoTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/2/3 09:18
 * @Description:
 */
@Component
public class TbCargoTypeServiceImpl implements TbCargoTypeService {

    @Resource
    private TbCargoTypeMapper tbCargoTypeMapper;

    @Override
    public List<TbCargoTypeDTO> getCargoTypeInfo() {
        QueryWrapper<TbCargoTypeDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        return tbCargoTypeMapper.getCargoTypeInfo(queryWrapper);
    }
}
