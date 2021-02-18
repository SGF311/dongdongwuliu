package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbSpecialDTO;
import com.dongdongwuliu.domain.vo.TbPathVO;
import com.dongdongwuliu.domain.vo.TbSpecialVO;
import com.dongdongwuliu.pojo.TbSpecial;
import com.dongdongwuliu.service.SpecialService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SpecialController
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/3 17:11
 * @Version 1.0
 **/
@RestController
@RequestMapping("specisl")
public class SpecialController {

    private static final Logger logger = LoggerFactory.getLogger(SpecialController.class);

    @Autowired
    private SpecialService specialService;

    @GetMapping("getInfo")
    @ApiOperation("查询所有专线")
    public DataResult getInfo(){
        try {
            List<TbSpecial> list = specialService.getInfo();
            // 检查集合中是否含有元素，如果数组中不存在任何元素，则返回true，否则返回false
            if (list.isEmpty()){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            String jsonString = JSONObject.toJSONString(list);
            List<TbSpecialVO> specialVOS = JSONObject.parseObject(jsonString, List.class);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(specialVOS);
        }catch (Exception e){
            logger.error("展示专线错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    //增加
    @PostMapping("/addSpecial")
    @ApiOperation("增加专线线路运输")
    @ApiImplicitParam(name = "tbSpecialVO", value = "专线线路运输实体",
            required = true, dataTypeClass = TbSpecialVO.class)
    public DataResult addSpecial(@RequestBody TbSpecialVO tbSpecialVO){
        try {
            TbSpecialDTO tbSpecialDTO = new TbSpecialDTO();
            BeanUtils.copyProperties(tbSpecialVO,tbSpecialDTO);
            specialService.addSpecial(tbSpecialDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("增加专线错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    //根据id查询
    @PostMapping("/selectById/{id}")
    @ApiOperation("根据专线ID查询")
    @ApiImplicitParam(name = "id", value = "专线ID", paramType = "path")
    public DataResult selectById(@PathVariable("id")Long id){
        try {
            TbSpecial tbSpecial = specialService.selectById(id);
            if (tbSpecial == null){
                return DataResult.response(ResponseStatusEnum.SUCCESS).setData(null);
            }
            TbSpecialVO tbSpecialVO = new TbSpecialVO();
            BeanUtils.copyProperties(tbSpecial,tbSpecialVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbSpecialVO);
        }catch (Exception e){
            logger.error("根据id查询专线错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    //修改
    @PutMapping("/updateBySpecial")
    @ApiOperation("修改专线线路")
    @ApiImplicitParam(name = "tbSpecialVO", value = "专线线路实体", paramType = "body",
            required = true, dataTypeClass = TbSpecialVO.class)
    public DataResult updateBySpecial(@RequestBody TbSpecialVO tbSpecialVO){
        try {
            TbSpecial tbSpecial = new TbSpecial();
            BeanUtils.copyProperties(tbSpecialVO,tbSpecial);
            Boolean b = specialService.updateBySpecial(tbSpecial);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("修改专线错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    //删除
    @DeleteMapping("/deleteById/{id}")
    @ApiOperation("根据专线ID删除")
    @ApiImplicitParam(name = "id", value = "专线ID", paramType = "path")
    public DataResult deleteById(@PathVariable("id")Long id){
        try {
            Boolean b = specialService.deleteById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(b);
        }catch (Exception e){
            logger.error("删除专线错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}

 

