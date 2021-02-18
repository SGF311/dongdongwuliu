package com.dongdongwuliu.controller;


import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.Service.TbContentService;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.MenuDTO;
import com.dongdongwuliu.domain.dto.TbContentDTO;
import com.dongdongwuliu.domain.vo.MenuVO;
import com.dongdongwuliu.domain.vo.TbContentVO;
import com.dongdongwuliu.pojo.TbContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("content")
@RestController
@Api(description = "广告接口")
@RefreshScope //开启自动刷新配置
public class TbContentController {
    Logger logger = LoggerFactory.getLogger(TbContentController.class);
    @Autowired
    private TbContentService tbContentService;

    //获取所有的数据
    @GetMapping("findAll")
    @ApiOperation(value = "获取所有的广告数据")
//    @ApiImplicitParam(name = "MenuVO", value = "实体类", required = true, paramType = "body", dataTypeClass = Integer.class)
    public DataResult findAll(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        logger.info("正在执行content/insertInto方法,参数为:{},{}", pageNumber, pageSize);
        Map<String, Object> map = null;
        try {
            map = tbContentService.pageInfo(pageNumber, pageSize);
        } catch (Exception e) {
            logger.error("content/insertInTo方法出错{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/insertInTo 方法执行完毕");
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(map);
    }

    //增加广告
    @PostMapping("insert")
    @ApiOperation(value = "增加广告数据")
    @ApiImplicitParam(name = "tbContent", value = "实体类", required = true, paramType = "body", dataTypeClass = TbContent.class)
    public DataResult insert(@RequestBody TbContentVO tbContent) {
        logger.info("正在执行content/insertInto方法,参数为:{}", tbContent);
        try {
            TbContentDTO tbContentDTO = new TbContentDTO();
            BeanUtils.copyProperties(tbContent, tbContentDTO);

            tbContentService.insert(tbContentDTO);
        } catch (Exception e) {
            logger.error("content/insertInTo方法出错{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/insertInTo 方法执行完毕");
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }

    //删除 deleteById
    @DeleteMapping("deleteById/{id}")
    @ApiOperation(value = "删除广告数据")
    @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path", dataTypeClass = Long.class)
    public DataResult deleteById(@PathVariable("id") Long id) {
        logger.info("正在执行content/deleteById方法,参数为:{}", id);
        try {
            tbContentService.deleteById(id);
        } catch (Exception e) {
            logger.error("content/deleteById方法出错{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/deleteById 方法执行完毕");
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }

    //多删  deleteBatch
    @DeleteMapping("deleteBatch/{ids}")
    @ApiOperation(value = "删除多条广告数据")
    @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path", dataTypeClass = Long.class)
    public DataResult deleteBatch(@PathVariable("ids") Long[] ids) {
        logger.info("正在执行content/deleteBatch方法,参数为:{}", ids);
        try {
            tbContentService.deleteBatch(ids);
        } catch (Exception e) {
            logger.error("content/ddeleteBatch方法出错{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/deleteBatch 方法执行完毕");
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }

    //根据id 查询数据  toUpdate
    @GetMapping("toUpdate/{id}")
    @ApiOperation(value = "根据id 查询数据")
    @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path", dataTypeClass = Long.class)
    public DataResult toUpdate(@PathVariable("id") Long id) {
        logger.info("正在执行content/toUpdate,参数为:{}", id);
        TbContentVO tbContentVO = new TbContentVO();
        try {
            TbContentDTO tbContentDTO = tbContentService.toUpdate(id);
            BeanUtils.copyProperties(tbContentDTO, tbContentVO);

        } catch (Exception e) {
            logger.error("content/toUpdate方法出错{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/toUpdate 方法执行完毕");
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbContentVO);
    }

    //修改
    @PutMapping("update")
    @ApiOperation(value = "增加广告数据")
    @ApiImplicitParam(name = "tbContent", value = "实体类", required = true, paramType = "body", dataTypeClass = TbContent.class)
    public DataResult update(@RequestBody TbContentVO tbContent) {
        logger.info("正在执行content/insertInto方法,参数为:{}", tbContent);
        try {
            TbContentDTO tbContentDTO = new TbContentDTO();
            BeanUtils.copyProperties(tbContent, tbContentDTO);

            tbContentService.update(tbContentDTO);
        } catch (Exception e) {
            logger.error("content/insertInTo方法出错{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/insertInTo 方法执行完毕");
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }

    //上下移动
    @PostMapping("/content/upDown")
    @ApiOperation(value = "上下移动")
//    @ApiImplicitParam(name = "tbContent", value = "实体类", required = true, paramType = "body", dataTypeClass = TbContent.class)
    public DataResult upDown(@RequestParam("id") Long id, @RequestParam("id") Integer flag) {
        logger.info("正在执行content/upDown方法,参数为:{},{}", id, flag);
        try {


            tbContentService.upDown(id, flag);
        } catch (Exception e) {
            logger.error("content/upDown方法出错{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/upDown 方法执行完毕");
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }

    @GetMapping("getContentByContentCategoryId/{contentCategoryId}")
    @ApiOperation(value = "查找页面广告")
    @ApiImplicitParam(name = "contentCategoryId", value = "contentCategoryId", required = true, paramType = "path", dataTypeClass = Long.class)
    public DataResult getContentByContentCategoryId(@PathVariable("contentCategoryId") Long contentCategoryId) {
        logger.info("正在执行content/getContentByContentCategoryId方法,参数为:{}", contentCategoryId);
        try {
            List<TbContentDTO> listDto = tbContentService.getContentByContentCategoryId(contentCategoryId);
            String s = JSONObject.toJSONString(listDto);
            List<TbContentVO> list = JSONObject.parseObject(s, List.class);
            logger.info("content/getContentByContentCategoryId 方法执行完毕");
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
        } catch (Exception e) {
            logger.error("content/getContentByContentCategoryId方法出错{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }

    }
}
