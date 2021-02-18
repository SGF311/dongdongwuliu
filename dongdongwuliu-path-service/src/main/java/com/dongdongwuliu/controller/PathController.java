package com.dongdongwuliu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.domain.dto.TbPathDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.domain.vo.TbPathVO;
import com.dongdongwuliu.domain.vo.TbSeckillDiscountCouponVO;
import com.dongdongwuliu.pojo.TbPath;
import com.dongdongwuliu.service.PathService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PathController
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/2 15:36
 * @Version 1.0
 **/
@RestController
@RequestMapping("pathController")
@Api
public class PathController {

    private static final Logger logger = LoggerFactory.getLogger(PathController.class);

    @Autowired
    private PathService pathService;


    @ApiOperation(value = "查询线路及价格")
    @GetMapping("/getInfo")
    public DataResult getInfo(){
        try {
            List<TbPathVO> list = pathService.getInfo();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
        }catch (Exception e){
            logger.error("展示路线错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/selectList")
    @ApiOperation(value = "线路展示并分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataTypeClass = Integer.class)
    })
    public DataResult selectList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "2") Integer pageSize){
        try {
            IPage<TbPath> page = pathService.selectList(pageNum,pageSize);
            List<TbPath> records = page.getRecords();
            System.out.println(records);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(records);
        }catch (Exception e){
            logger.error("展示路线错误:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    //增加线路信息
    @PostMapping("/insertInfoPath")
    @ApiOperation(value = "增加线路信息")
    @ApiImplicitParam(name = "tbPathVO", value = "实体类", required = true, dataTypeClass = TbPathVO.class)
    public DataResult insertInfoPath(@RequestBody TbPathVO tbPathVO){
        try {
            TbPathDTO tbPathDTO = new TbPathDTO();
            BeanUtils.copyProperties(tbPathVO,tbPathDTO);
            pathService.insertInfoPath(tbPathDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("增加失败:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    //回显
    @ApiOperation("根据id查询线路")
    @ApiImplicitParam(name = "id",value = "id",required = true,paramType = "path",dataTypeClass = Long.class)
    @GetMapping("/selectInfoById/{id}")
    public DataResult<TbPathVO> selectInfoById(@PathVariable("id")Long id){
        try {
            TbPathDTO tbPathDTO = pathService.selectInfoById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbPathDTO);
        }catch (Exception e){
            logger.error("根据id查询失败:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation("线路修改,根据实体修改线路")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbPathVO", value = "线路实体", required = true, paramType = "body", dataTypeClass = TbPathVO.class)
    })
    @PutMapping("/updatePathInfo")
    public DataResult updatePathInfo(TbPathVO tbPathVO){
        try {
            TbPathDTO tbPathDTO = new TbPathDTO();
            BeanUtils.copyProperties(tbPathVO,tbPathDTO);
            pathService.updatePathInfo(tbPathDTO);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        }catch (Exception e){
            logger.error("根据实体修改失败:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }




    //根据id删除
    @DeleteMapping("/deleteByPath/{id}")
    @ApiOperation(value = "删除路线信息")
    @ApiImplicitParam(name = "id", value = "线路id", required = true, paramType = "path",
            dataTypeClass = Long.class)
    public DataResult deleteByPath(@PathVariable("id")Long id){
        try {
            Integer integer = pathService.deleteByPath(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(integer);
        }catch (Exception e){
            logger.error("根据实体删除失败:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


    //根据省查询
    //根据id删除
    @GetMapping("/selectStartpidByInfo/{startPid}/{endPid}")
    @ApiOperation(value = "查询开始省信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startPid", value = "开始省id", required = true, paramType = "path",
                    dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "endPid", value = "结束省id", required = true, paramType = "path",
                    dataTypeClass = Integer.class)
    })
    public DataResult selectStartpidByInfo(@PathVariable("startPid")Integer startPid,@PathVariable("endPid")Integer endPid){
        try {
            DataResult<TbPath> tbPath = pathService.selectStartpidByInfo(startPid,endPid);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbPath);
        }catch (Exception e){
            logger.error("根据省查询失败:{}",e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }



}

 

