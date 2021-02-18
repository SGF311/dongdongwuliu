package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongdongwuliu.dao.PathMapper;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbPathDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbPathVO;
import com.dongdongwuliu.pojo.TbPath;
import com.dongdongwuliu.service.PathService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName PathServiceImpl
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/2 15:37
 * @Version 1.0
 **/
@Service
public class PathServiceImpl implements PathService {

    @Resource
    private PathMapper pathMapper;

    //分页查询
    @Override
    public IPage<TbPath> selectList(Integer pageNum, Integer pageSize) {
        QueryWrapper<TbPath> queryWrapper = new QueryWrapper<>();
        Page<TbPath> tbCarPage = new Page<>(pageNum,pageSize);
        IPage<TbPath> tbCarIPage = pathMapper.selectPage(tbCarPage, queryWrapper);
        return tbCarIPage;
    }

    //增加
    @Override
    public void insertInfoPath(TbPathDTO tbPathDTO) {
        TbPath tbPath = new TbPath();
        BeanUtils.copyProperties(tbPathDTO,tbPath);
        pathMapper.insert(tbPath);
    }

    //回显
    @Override
    public TbPathDTO selectInfoById(Long id) {
        TbPath tbPath = pathMapper.selectById(id);
        TbPathDTO tbPathDTO = new TbPathDTO();
        BeanUtils.copyProperties(tbPath,tbPathDTO);
        return tbPathDTO;
    }

    @Override
    public void updatePathInfo(TbPathDTO tbPathDTO) {
        TbPath tbPath = new TbPath();
        BeanUtils.copyProperties(tbPathDTO,tbPath);
        pathMapper.selectById(tbPath);
    }

    @Override
    public Integer deleteByPath(Long id) {

       return pathMapper.deleteById(id);
    }

    //查询线路及价格
    @Override
    public List<TbPathVO> getInfo() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select();

        return pathMapper.getInfo(queryWrapper);
    }

    @Override
    public DataResult<TbPath> selectStartpidByInfo(Integer startPid, Integer endPid) {
        TbPath tbPath = pathMapper.selectStartpidByInfo(startPid,endPid);
        DataResult<TbPath> dataResult = new DataResult<>();
        dataResult.setData(tbPath);
        return dataResult;
    }
}

 

