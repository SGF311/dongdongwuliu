package com.dongdongwuliu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbPathDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbPathVO;
import com.dongdongwuliu.pojo.TbPath;
import io.swagger.models.auth.In;

import java.util.List;

public interface PathService {
    IPage<TbPath> selectList(Integer pageNum, Integer pageSize);

    void insertInfoPath(TbPathDTO tbPathDTO);

    TbPathDTO selectInfoById(Long id);

    void updatePathInfo(TbPathDTO tbPathDTO);

    Integer deleteByPath(Long id);

    List<TbPathVO> getInfo();

    DataResult<TbPath> selectStartpidByInfo(Integer startPid, Integer endPid);
}
