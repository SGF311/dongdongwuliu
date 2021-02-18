package com.dongdongwuliu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.mapper.TbSiteMapper;
import com.dongdongwuliu.pojo.TbSite;
import com.dongdongwuliu.service.TbSiteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/2/2 12:59
 * @Version 1.0
 **/
@Service
public class TbSiteServiceImpl implements TbSiteService {
    @Autowired
    private TbSiteMapper tbSiteMapper;

    //添加站点信息
    @Override
    public void insertInfoSite(TbSiteDTO tbSiteDTO) {
        TbSite tbSite = new TbSite();
        BeanUtils.copyProperties(tbSiteDTO,tbSite);
        tbSiteMapper.insert(tbSite);
    }
    //删除站点信息
    @Override
    public void deleteBySiteId(Integer siteId) {
        tbSiteMapper.deleteById(siteId);
    }

    //修改站点信息
    @Override
    public void updateInfoSite(TbSiteDTO tbSiteDTO) {
        TbSite tbSite = new TbSite();
        BeanUtils.copyProperties(tbSiteDTO,tbSite);
        tbSiteMapper.updateById(tbSite);
    }

    @Override
    public Map<String, Object> selectByIdOrSiteName(TbSiteDTO tbSiteDTO, Integer pageNumber, Integer pageSize) {
        TbSite tbSite = new TbSite();
        BeanUtils.copyProperties(tbSiteDTO,tbSite);
        //站点的名称
        String siteName = tbSite.getSiteName();
        //站点的地址
        String pid = tbSite.getPid();
        String cid = tbSite.getCid();
        String aid = tbSite.getAid();

        Page<TbSite> tbCarDTOPage = new Page<>(pageNumber,pageSize);
        QueryWrapper<TbSite> queryWrapper = new QueryWrapper<>();

        //根据条件查询并分页
        IPage<TbSite> tbSiteIPage = null;
        //根据站点名模糊查询
        if (siteName != null && !siteName.equals("")){
            queryWrapper.likeRight("site_name",siteName);
            tbSiteIPage = tbSiteMapper.selectPage(tbCarDTOPage, queryWrapper);
            List<TbSite> list = tbSiteIPage.getRecords();
            long total = tbSiteIPage.getTotal();
            long pages = tbSiteIPage.getPages();
            long current = tbSiteIPage.getCurrent();
            long size = tbSiteIPage.getSize();
            Map<String, Object> map = new HashMap<>();
            map.put("rows", list);
            map.put("total", total);
            map.put("pageNumber", current);
            map.put("pageSize", size);
            map.put("pages", pages);
            return map;
        }
        //根据地址进行查询
        if (pid != null && !pid.equals("") && cid != null && !cid.equals("") && aid != null && !aid.equals("")){
            queryWrapper.eq("pid",pid);
            queryWrapper.eq("cid",cid);
            queryWrapper.eq("aid",aid);
            tbSiteIPage = tbSiteMapper.selectPage(tbCarDTOPage, queryWrapper);
            List<TbSite> list = tbSiteIPage.getRecords();
            long total = tbSiteIPage.getTotal();
            long pages = tbSiteIPage.getPages();
            long current = tbSiteIPage.getCurrent();
            long size = tbSiteIPage.getSize();
            Map<String, Object> map = new HashMap<>();
            map.put("rows", list);
            map.put("total", total);
            map.put("pageNumber", current);
            map.put("pageSize", size);
            map.put("pages", pages);
            return map;
        }
        if (pid != null && !pid.equals("") && cid != null && !cid.equals("")){
            queryWrapper.eq("pid",pid);
            queryWrapper.eq("cid",cid);
            tbSiteIPage = tbSiteMapper.selectPage(tbCarDTOPage, queryWrapper);
            List<TbSite> list = tbSiteIPage.getRecords();
            long total = tbSiteIPage.getTotal();
            long pages = tbSiteIPage.getPages();
            long current = tbSiteIPage.getCurrent();
            long size = tbSiteIPage.getSize();
            Map<String, Object> map = new HashMap<>();
            map.put("rows", list);
            map.put("total", total);
            map.put("pageNumber", current);
            map.put("pageSize", size);
            map.put("pages", pages);
            return map;
        }
        if (pid != null && !pid.equals("")){
            queryWrapper.eq("pid",pid);
            tbSiteIPage = tbSiteMapper.selectPage(tbCarDTOPage, queryWrapper);
            List<TbSite> list = tbSiteIPage.getRecords();
            long total = tbSiteIPage.getTotal();
            long pages = tbSiteIPage.getPages();
            long current = tbSiteIPage.getCurrent();
            long size = tbSiteIPage.getSize();
            Map<String, Object> map = new HashMap<>();
            map.put("rows", list);
            map.put("total", total);
            map.put("pageNumber", current);
            map.put("pageSize", size);
            map.put("pages", pages);
            return map;
        }
        tbSiteIPage = tbSiteMapper.selectPage(tbCarDTOPage, null);

        List<TbSite> list = tbSiteIPage.getRecords();
        long total = tbSiteIPage.getTotal();
        long pages = tbSiteIPage.getPages();
        long current = tbSiteIPage.getCurrent();
        long size = tbSiteIPage.getSize();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", total);
        map.put("pageNumber", current);
        map.put("pageSize", size);
        map.put("pages", pages);
        return map;
    }

    @Override
    public TbSiteDTO selectById(Integer siteId) {
        TbSite tbSite = tbSiteMapper.selectById(siteId);
        TbSiteDTO tbSiteDTO = new TbSiteDTO();
        BeanUtils.copyProperties(tbSite,tbSiteDTO);
        return tbSiteDTO;
    }

    @Override
    public List<TbSite> selectAll() {
        List<TbSite> tbSites = tbSiteMapper.selectList(null);
        return tbSites;
    }

    @Override
    public List<TbSite> selectByCid(String cid) {
        QueryWrapper<TbSite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        List<TbSite> tbSites = tbSiteMapper.selectList(queryWrapper);
        return tbSites;
    }

    @Override
    public List<TbSite> selectByAid(String aid) {
        QueryWrapper<TbSite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
        List<TbSite> tbSites = tbSiteMapper.selectList(queryWrapper);
        return tbSites;
    }

    //前台展示所有信息 并分页
//    @Override
//    public IPage<TbSite> webSelectAll(Integer pageNum, Integer pageSize) {
//        Page<TbSite> page = new Page<>(pageNum,pageSize);
//        IPage<TbSite> tbSiteIPage = tbSiteMapper.selectPage(page, null);
//        List<TbSite> tbSites = tbSiteMapper.selectList(null);
//        return tbSiteIPage;
//    }
    //前台展示所有信息 不分页
    @Override
    public List<TbSite> webSelectAll() {
//        List<TbSite> tbSites = tbSiteMapper.selectList(null);
        QueryWrapper<TbSite> queryWrapper = new QueryWrapper<>();
        List<TbSite> tbSites = tbSiteMapper.selectAll(queryWrapper);
        return tbSites;
    }
    //搜素查询
    @Override
    public List<TbSite> searchSite(TbSiteDTO tbSiteDTO) {
        TbSite tbSite = new TbSite();
        BeanUtils.copyProperties(tbSiteDTO,tbSite);
        //站点的名称
        String siteName = tbSite.getSiteName();
        //站点的地址
        String pid = tbSite.getPid();
        String cid = tbSite.getCid();
        String aid = tbSite.getAid();

        QueryWrapper<TbSite> queryWrapper = new QueryWrapper<>();

        //根据条件查询并分页
        List<TbSite> tbSites = null;
        //根据站点名模糊查询
        if (siteName != null && !siteName.equals("")){
            queryWrapper.likeRight("site_name",siteName);
            tbSites = tbSiteMapper.selectAll(queryWrapper);
            return tbSites;
        }
        //根据地址进行查询
        if (pid != null && !pid.equals("") && cid != null && !cid.equals("") && aid != null && !aid.equals("")){
            queryWrapper.eq("pid",pid);
            queryWrapper.eq("cid",cid);
            queryWrapper.eq("aid",aid);
            tbSites = tbSiteMapper.selectAll(queryWrapper);
            return tbSites;
        }
        if (pid != null && !pid.equals("") && cid != null && !cid.equals("")){
            queryWrapper.eq("pid",pid);
            queryWrapper.eq("cid",cid);
            tbSites = tbSiteMapper.selectAll(queryWrapper);
            return tbSites;
        }
        if (pid != null && !pid.equals("")){
            queryWrapper.eq("pid",pid);
            tbSites = tbSiteMapper.selectAll(queryWrapper);
            return tbSites;
        }
       return null;
    }
}