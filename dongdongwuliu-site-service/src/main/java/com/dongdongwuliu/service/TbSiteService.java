package com.dongdongwuliu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.pojo.TbSite;

import java.util.List;
import java.util.Map;

public interface TbSiteService {
    void insertInfoSite(TbSiteDTO tbSiteDTO);

    void deleteBySiteId(Integer siteId);

    void updateInfoSite(TbSiteDTO tbSiteDTO);

    Map<String, Object> selectByIdOrSiteName(TbSiteDTO tbSiteDTO, Integer pageNumber, Integer pageSize);

    TbSiteDTO selectById(Integer siteId);

    List<TbSite> selectAll();

    List<TbSite> selectByCid(String cid);

    List<TbSite> selectByAid(String aid);


    //前台展示所有信息
//    IPage<TbSite> webSelectAll(Integer pageNum, Integer pageSize);
    List<TbSite> webSelectAll();

    List<TbSite> searchSite(TbSiteDTO tbSiteDTO);
}
