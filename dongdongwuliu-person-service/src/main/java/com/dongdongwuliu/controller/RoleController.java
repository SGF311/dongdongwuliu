package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.dto.TbRoleDTO;
import com.dongdongwuliu.domain.vo.MenuVO;
import com.dongdongwuliu.pojo.TbRole;
import com.dongdongwuliu.service.TbRoleService;
import com.dongdongwuliu.domain.vo.TbRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Deacription TODO
 * @Author Lenovo
 * @Date 2020/12/6 17:48
 * @Version 1.0
 **/
@RestController
@RequestMapping("role")
@Api(description = "角色接口")
@RefreshScope //开启自动刷新配置
public class RoleController {
    Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private TbRoleService tRoleService;


    //查找全部
    @GetMapping("getInfo")
    @ApiOperation(value = "获取角色表全部数据")
    public DataResult getInfo() {
        logger.info("执行role/getInfo方法");
        try {
            List<TbRole> list = tRoleService.findAll();
            logger.info("执行role/getInfo方法结束");
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
        } catch (Exception e) {
            logger.error("执行role/getInfo方法结束,错误{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //新增  ids是menu id
    @PostMapping("insertInto")
    @ApiOperation(value = "增加角色表数据")
    @ApiImplicitParam(name = "role", value = "实体类", required = true, paramType = "body", dataTypeClass = TbRoleVO.class)
    public DataResult insertInTo(@RequestBody TbRoleVO role, @RequestParam Integer[] ids) {
        try {
            logger.error("执行role/insertInto方法,参数:{},{}", role, ids);
            TbRoleDTO tbRoleDTO = new TbRoleDTO();
            BeanUtils.copyProperties(role, tbRoleDTO);
            tRoleService.insertInTo(tbRoleDTO, ids);
            logger.error("执行role/insertInto方法结束");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            logger.error("执行role/insertInto方法结束,错误{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    ////根据id查询
    @GetMapping("toUpdate/{rid}")
    @ApiOperation(value = "根据id查找角色表数据")
    @ApiImplicitParam(name = "rid", value = "角色id", required = true, paramType = "path", dataTypeClass = Integer.class)
    public DataResult toupdate(@PathVariable("rid") Integer rid) {
        logger.info("执行role/toUpdate方法,参数:{}", rid);
        try {
            //获取id 对应的角色
            TbRoleDTO role = tRoleService.getInfoById(rid);
            TbRoleVO tbRoleVO = new TbRoleVO();
            BeanUtils.copyProperties(role, tbRoleVO);
            logger.info("执行role/toUpdate方法结束");
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbRoleVO);
        } catch (Exception e) {
            logger.error("执行role/toUpdate方法结束,错误{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    ////修改
    @PutMapping("update")
    @ApiOperation(value = "修改角色表数据")
    @ApiImplicitParam(name = "role", value = "实体类", required = true, paramType = "body", dataTypeClass = TbRoleDTO.class)
    public DataResult update(@RequestBody TbRoleDTO role, @RequestParam Integer[] ids) {
        logger.info("执行role/update方法,参数:{},{}", role, ids);
        try {
            tRoleService.update(role, ids);
            logger.info("执行role/update方法结束");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            logger.error("执行role/update方法结束,错误{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    ////删除
    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "权限id", required = true, paramType = "path", dataTypeClass = Integer.class)
    public DataResult delete(@PathVariable("id") Integer id) {
        logger.info("执行role/delete方法,参数:{}", id);
        try {
            tRoleService.delete(id);
            logger.info("执行role/delete方法,参数:{}", id);
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            logger.error("执行role/delete方法结束,错误{}", e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

}
