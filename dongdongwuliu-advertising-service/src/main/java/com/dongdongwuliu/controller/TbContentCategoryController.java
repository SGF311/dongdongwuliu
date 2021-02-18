package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.Service.TbContentCategoryService;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbContentCategoryDTO;
import com.dongdongwuliu.domain.dto.TbContentDTO;
import com.dongdongwuliu.domain.vo.TbContentCategoryVO;
import com.dongdongwuliu.domain.vo.TbContentVO;
import com.dongdongwuliu.pojo.TbContent;
import com.dongdongwuliu.pojo.TbContentCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RequestMapping("contentCategory")
@RestController
@Api(description = "广告内容接口")
@RefreshScope //开启自动刷新配置
public class TbContentCategoryController {

    Logger logger = LoggerFactory.getLogger(TbContentController.class);
    @Autowired
    private TbContentCategoryService tbContentCategoryService;
    //查询全部
    //获取所有的数据
    @GetMapping("findAll")
    @ApiOperation(value = "获取所有的广告内容数据")
    public DataResult findAll() {
        logger.info("contentCategory/findAll方法,");
        List<TbContentCategoryVO> voList = null;
        try {
            List<TbContentCategoryDTO> list = tbContentCategoryService.findAll();
            String s = JSONObject.toJSONString(list);
            voList = JSONObject.parseObject(s, List.class);
        } catch (Exception e) {
            logger.error("content/findAll方法出错{}" ,e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/findAll方法执行完毕" );
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(voList);
    }

    //增加 insert
    //获取所有的数据
    @PostMapping("insert")
    @ApiOperation(value = "增加数据")
    @ApiImplicitParam(name = "tbContentCategoryVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbContentCategoryVO.class)
    public DataResult insert(@RequestBody TbContentCategoryVO tbContentCategoryVO) {
        logger.info("contentCategory/insert方法,");
        try {
            String toJSONString = JSONObject.toJSONString(tbContentCategoryVO);
            TbContentCategoryDTO tbContentCategoryDTO = JSONObject.parseObject(toJSONString, TbContentCategoryDTO.class);
             tbContentCategoryService.insert(tbContentCategoryDTO);
        } catch (Exception e) {
            logger.error("content/insertInTo方法出错{}" ,e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/insertInTo 方法执行完毕" );
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }
    //shanchu
    //删除 deleteById
    @DeleteMapping("deleteById/{id}")
    @ApiOperation(value = "删除广告数据")
    @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path", dataTypeClass = Long.class)
    public DataResult deleteById(@PathVariable("id")Long id) {
        logger.info("正在执行content/deleteById方法,参数为:{}",id);
        try {
            tbContentCategoryService.deleteById(id);
        } catch (Exception e) {
            logger.error("content/deleteById方法出错{}" ,e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/deleteById 方法执行完毕" );
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }
    //多删  deleteBatch
    @DeleteMapping("deleteBatch/{ids}")
    @ApiOperation(value = "删除多条广告数据")
    @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path", dataTypeClass = Long.class)
    public DataResult deleteBatch(@PathVariable("ids")Long[] ids) {
        logger.info("正在执行content/deleteBatch方法,参数为:{}",ids);
        try {
            tbContentCategoryService.deleteBatch(ids);
        } catch (Exception e) {
            logger.error("content/ddeleteBatch方法出错{}" ,e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/deleteBatch 方法执行完毕" );
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }
    //根据id 查询数据  toUpdate
    @GetMapping("toUpdate/{id}")
    @ApiOperation(value = "根据id 查询数据")
    @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path", dataTypeClass = Long.class)
    public DataResult toUpdate(@PathVariable("id")Long id) {
        logger.info("正在执行content/toUpdate,参数为:{}",id);
        TbContentCategoryVO tbContentCategoryVO = new TbContentCategoryVO();
        try {
            TbContentCategoryDTO tbContentCategoryDTO = tbContentCategoryService.toUpdate(id);
            BeanUtils.copyProperties(tbContentCategoryDTO,tbContentCategoryVO);

        } catch (Exception e) {
            logger.error("content/toUpdate方法出错{}" ,e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/toUpdate 方法执行完毕" );
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbContentCategoryVO);
    }

    //修改
    @PutMapping("update")
    @ApiOperation(value = "增加广告数据")
    @ApiImplicitParam(name = "tbContentCategoryVO", value = "实体类", required = true, paramType = "body", dataTypeClass = TbContentCategoryVO.class)
    public DataResult update(@RequestBody  TbContentCategoryVO tbContentCategoryVO) {
        logger.info("正在执行content/insertInto方法,参数为:{}",tbContentCategoryVO);
        try {
            TbContentCategoryDTO tbContentCategoryDTO =  new TbContentCategoryDTO();
            BeanUtils.copyProperties(tbContentCategoryVO,tbContentCategoryDTO);

            tbContentCategoryService.update(tbContentCategoryDTO);
        } catch (Exception e) {
            logger.error("content/insertInTo方法出错{}" ,e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("content/insertInTo 方法执行完毕" );
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }
}
