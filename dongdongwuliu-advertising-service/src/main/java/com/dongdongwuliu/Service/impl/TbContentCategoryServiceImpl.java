package com.dongdongwuliu.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.Service.TbContentCategoryService;
import com.dongdongwuliu.controller.TbContentController;
import com.dongdongwuliu.domain.dto.TbContentCategoryDTO;
import com.dongdongwuliu.mapper.TbContentCategoryMapper;
import com.dongdongwuliu.pojo.TbContentCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Resource
    private TbContentCategoryMapper tbContentControllerMapper;

    @Override
    public List<TbContentCategoryDTO> findAll() {
        List<TbContentCategoryDTO> tbContentCategoryDTO = null;
        try {
            List<TbContentCategory> tbContentCategory = tbContentControllerMapper.selectList(null);
            String s = JSONObject.toJSONString(tbContentCategory);
            tbContentCategoryDTO = JSONObject.parseObject(s, List.class);
            return tbContentCategoryDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void insert(TbContentCategoryDTO tbContentCategoryDTO) {

        try {
            TbContentCategory tbContentCategory = new TbContentCategory();
            BeanUtils.copyProperties(tbContentCategoryDTO, tbContentCategory);
            tbContentControllerMapper.insert(tbContentCategory);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            tbContentControllerMapper.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteBatch(Long[] ids) {
        try {
            List<Long> longs = (List<Long>) Arrays.asList(ids);
            tbContentControllerMapper.deleteBatchIds(longs);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public TbContentCategoryDTO toUpdate(Long id) {
        TbContentCategoryDTO tbContentCategoryDTO = new TbContentCategoryDTO();
        try {
            TbContentCategory tbContentCategory = tbContentControllerMapper.selectById(id);

            BeanUtils.copyProperties(tbContentCategory,tbContentCategoryDTO);
            return tbContentCategoryDTO;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void update(TbContentCategoryDTO tbContentCategoryDTO) {
        try {
            TbContentCategory tbContentCategory = new TbContentCategory();
            BeanUtils.copyProperties(tbContentCategoryDTO,tbContentCategory);
            tbContentControllerMapper.updateById(tbContentCategory);
        } catch (Exception e) {
            throw e;
        }
    }
}
