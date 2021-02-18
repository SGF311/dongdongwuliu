package com.dongdongwuliu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.pojo.TbSite;
import com.dongdongwuliu.service.TbSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/2/2 13:01
 * @Version 1.0
 **/
@RestController
@RequestMapping("siteController")
@Api(description = "站点接口")
@RefreshScope //开启自动刷新配置
public class SiteController {
    //出现异常也要打印日志
    private Logger logger = LoggerFactory.getLogger(SiteController.class);
    @Autowired
    private TbSiteService tbSiteService;

    //增加站点信息
    @PostMapping("insertInfoSite")
    @ApiOperation(value = "增加站点信息")
    @ApiImplicitParam(name = "tbSiteVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbSiteVO.class)
    public DataResult insertInfoSite(@RequestBody TbSiteVO tbSiteVO){
        try {
            TbSiteDTO tbSiteDTO = new TbSiteDTO();
            BeanUtils.copyProperties(tbSiteVO,tbSiteDTO);
            tbSiteService.insertInfoSite(tbSiteDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("添加失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //删除站点信息
    @DeleteMapping("deleteBySiteId")
    @ApiOperation(value = "删除站点信息")
    @ApiImplicitParam(name = "siteId", value = "站点id", required = true, paramType = "query", dataTypeClass = Integer.class)
    public DataResult deleteBySiteId(@RequestParam("siteId")Integer siteId){
        try {
            tbSiteService.deleteBySiteId(siteId);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("删除失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //修改站点信息
    @PutMapping("updateInfoSite")
    @ApiOperation(value = "修改站点信息")
    @ApiImplicitParam(name = "tbSiteVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbSiteVO.class)
    public DataResult updateInfoSite(@RequestBody TbSiteVO tbSiteVO){
        try {
            TbSiteDTO tbSiteDTO = new TbSiteDTO();
            BeanUtils.copyProperties(tbSiteVO,tbSiteDTO);
            tbSiteService.updateInfoSite(tbSiteDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("修改失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据地址或者站点名称进行查询
    @PostMapping("selectByIdOrSiteName")
    @ApiOperation(value = "展示站点信息并分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbSiteVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbSiteVO.class),
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataTypeClass = Integer.class)
    })
    public DataResult selectByIdOrSiteName(@RequestBody TbSiteVO tbSiteVO,@RequestParam("pageNumber") Integer pageNumber,@RequestParam("pageSize") Integer pageSize){
        try {
            TbSiteDTO tbSiteDTO = new TbSiteDTO();
            BeanUtils.copyProperties(tbSiteVO,tbSiteDTO);
            Map<String, Object> map = tbSiteService.selectByIdOrSiteName(tbSiteDTO, pageNumber, pageSize);
//            List<TbSite> list = tbSiteIPage.getRecords();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(map);
        }catch (Exception e){
            logger.error("查询失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据id查询
    @PostMapping("selectById")
    @ApiOperation(value = "根据站点id查询")
    @ApiImplicitParam(name = "siteId", value = "站点id", required = true, paramType = "query", dataTypeClass = Integer.class)
    public DataResult selectById(@RequestParam("siteId") Integer siteId){
        try {
            TbSiteDTO tbSiteDTO = tbSiteService.selectById(siteId);
            TbSiteVO tbSiteVO = new TbSiteVO();
            BeanUtils.copyProperties(tbSiteDTO,tbSiteVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSiteVO);
        }catch (Exception e){
            logger.error("查询失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //查询全部的数据
    @GetMapping("selectAll")
    @ApiOperation(value = "查询全部")
    public DataResult selectAll(){
        try {
            List<TbSite> tbSites = tbSiteService.selectAll();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSites);
        }catch (Exception e){
            logger.error("查询所有失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据市 id查询所有的相关站点
    @GetMapping("selectByCid")
    @ApiOperation(value = "根据市 cid查询所有的相关站点")
    @ApiImplicitParam(name = "cid", value = "市cid", required = true, paramType = "query", dataTypeClass = String.class)
    public DataResult selectByCid(@RequestParam("cid") String cid){
        try {
            List<TbSite> tbSites = tbSiteService.selectByCid(cid);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSites);
        }catch (Exception e){
            logger.error("查询所有失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //根据县 aid查询所有的相关站点
    @GetMapping("selectByAid")
    @ApiOperation(value = "根据县aid查询所有的相关站点")
    @ApiImplicitParam(name = "aid", value = "县aid", required = true, paramType = "query", dataTypeClass = String.class)
    public DataResult selectByAid(@RequestParam("aid") String aid){
        try {
            List<TbSite> tbSites = tbSiteService.selectByAid(aid);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSites);
        }catch (Exception e){
            logger.error("查询所有失败",e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

//--------------------------------
    //前台查询所有
    @GetMapping("webSelectAll")
    @ApiOperation(value = "网页显示站点信息")
//    public DataResult webSelectAll(@RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize){
    public DataResult webSelectAll(){
        try {
            //带分页
            //IPage<TbSite> page = tbSiteService.webSelectAll(pageNum,pageSize);
            //List<TbSite> list = page.getRecords();
            List<TbSite> tbSites = tbSiteService.webSelectAll();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSites);
        }catch (Exception e){
            logger.error("查询失败");
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //前台页面的搜索
    @PostMapping("searchSite")
    @ApiOperation(value = "网页搜索站点信息")
    public DataResult searchSite(@RequestBody TbSiteVO tbSiteVO){
        try {
            TbSiteDTO tbSiteDTO = new TbSiteDTO();
            BeanUtils.copyProperties(tbSiteVO,tbSiteDTO);
            List<TbSite> listSite = tbSiteService.searchSite(tbSiteDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(listSite);
        }catch (Exception e){
            logger.error("查询失败");
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

}