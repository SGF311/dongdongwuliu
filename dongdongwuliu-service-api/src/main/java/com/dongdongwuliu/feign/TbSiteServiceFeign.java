package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.feign.fallback.TbSiteServiceFeignImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "dongdongwuliu-site-service", fallback = TbSiteServiceFeignImplFallBack.class,url = "http://127.0.0.1:8050")
public interface TbSiteServiceFeign {

    //根据站点名称或者是地址进行查询
    @PostMapping("/siteController/selectByIdOrSiteName")
    DataResult selectByIdOrSiteName(@RequestBody TbSiteDTO tbSiteDTO, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize);

    //增加站点信息
    @PostMapping("/siteController/insertInfoSite")
    DataResult addSiteInfo(@RequestBody TbSiteDTO tbSiteDTO);

    //查询需要修改的
    @PostMapping("/siteController/selectById")
    DataResult toUpdate(@RequestParam("siteId")Integer siteId);

    //修改信息
    @PutMapping("/siteController/updateInfoSite")
    DataResult updateInfoSite(TbSiteVO tbSiteVO);

    //删除信息
    @DeleteMapping("/siteController/deleteBySiteId")
    DataResult deleteBySiteId(@RequestParam Integer siteId);

    //查询所有的站点信息,用于车辆增加时的绑定,下拉列表框
    @GetMapping("/siteController/selectAll")
    DataResult selectAll();

    //根据市 id查询所有的站点
    @GetMapping("/siteController/selectByCid")
    DataResult selectByCid(@RequestParam String cid);

    //根据县 id查询所有的站点
    @GetMapping("/siteController/selectByAid")
    DataResult selectByAid(@RequestParam String aid);
//---------------------------------------------------------
    //前端页面查询站点信息  带分页
    @GetMapping("/siteController/webSelectAll")
    DataResult webSelectAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
    //前台的搜索站点
    @PostMapping("/siteController/searchSite")
    DataResult searchSite(@RequestBody TbSiteVO tbSiteVO);
}
