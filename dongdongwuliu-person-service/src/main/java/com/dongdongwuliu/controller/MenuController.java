package com.dongdongwuliu.controller;


import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.MenuDTO;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.pojo.Menu;
import com.dongdongwuliu.service.MenuService;
import com.dongdongwuliu.domain.vo.MenuVO;
import com.dongdongwuliu.service.TbPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import sun.net.www.http.KeepAliveStream;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author Lenovo
 * @Date 2020/12/6 15:19
 * @Version 1.0
 **/

@RequestMapping("menu")
@RestController
@Api(description = "权限接口")
@RefreshScope //开启自动刷新配置
public class MenuController {
    Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TbPersonService tbPersonService;


    /**
     * @param request 获取菜单  还有前台传过来的redis的key
     * @return
     */

    @GetMapping("getMenu")
    @ApiOperation(value = "获取菜单")
    public DataResult getMenu(@RequestParam("key") String key,HttpServletRequest request) {
        logger.info("正在执行menu/getMenu方法");
        List<Map<String, Object>> list = new ArrayList();
        try {

            //从redis中获取key
            Object o = redisTemplate.opsForValue().get(key);

            //获取当前的登陆对象 应该从redis中获取
            TbPersonDTO person = JSONObject.parseObject(o.toString(),TbPersonDTO.class);
//            TbPersonDTO person = tbPersonService.getInfoByName(keyVlue);
            if(person == null){
                return DataResult.response(ResponseStatusEnum.USER_NOT_FOUND);
            }
            list = menuService.getMenu(person);


        } catch (Exception e) {
            logger.error("menu/getMenu方法错误", e);
        }
        logger.info("menu/getMenu方法执行完毕,返回值为: " + list);
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
    }


    //获取权限树
    @GetMapping("getZtree")
    @ApiOperation(value = "获取菜单权限表")
    public DataResult getZtree() {
        logger.info("正在执行menu/getZtree方法");
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = menuService.getZtree();
        } catch (Exception e) {
            logger.error("menu/getZtree方法错误", e);
        }
        logger.info("menu/getZtree方法执行完毕,返回值为: " + list);
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
    }


    //  增加权限树
    @PostMapping("insertInTo")
    @ApiOperation(value = "增加菜单权限表")
    @ApiImplicitParam(name = "MenuVO", value = "实体类", required = true, paramType = "body", dataTypeClass = MenuVO.class)
    public DataResult insertInTo(@RequestBody MenuVO menuvo) {
        logger.info("正在执行menu/insertInto方法,参数为:" + menuvo);
        try {
            MenuDTO menuDTO = new MenuDTO();
            BeanUtils.copyProperties(menuvo,menuDTO);
            menuService.insertInto(menuDTO);

        } catch (Exception e) {
            logger.error("menu/insertInTo方法出错{}" ,e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
        logger.info("menu/insertInTo 方法执行完毕" );
        return DataResult.response(ResponseStatusEnum.SUCCESS);
    }



    //根据id查找数据
    @GetMapping("toUpdate/{mid}")
    @ApiOperation(value = "根据id查找权限表数据")
    @ApiImplicitParam(name = "mid", value = "权限id", required = true, paramType = "path", dataTypeClass = Integer.class)
    public DataResult toUpdate(@PathVariable("mid") Integer mid) {
        logger.info("正在执行menu/toUpdate,参数为:" + mid);
        Menu menu = menuService.getInfoById(mid);
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menu,menuVO);
        logger.info("执行menu/toUpdate完毕,参数为:{}" , menuVO);
        return  DataResult.response(ResponseStatusEnum.SUCCESS).setData(menuVO);
    }

    //修改数据
    @PutMapping("update")
    @ApiOperation(value = "修改查找权限表数据")
    @ApiImplicitParam(name = "menu", value = "实体类", required = true, paramType = "body", dataTypeClass = MenuVO.class)
    public DataResult update(@RequestBody MenuVO menu) {
        logger.info("执行menu/update,参数为:{}" , menu);
        try {
            MenuDTO menuDTO = new MenuDTO();
            BeanUtils.copyProperties(menu,menuDTO);
            menuService.update(menuDTO);
            logger.info("执行menu/update结束");
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            logger.error("执行menu/update结束,错误为:{}" , e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //删除
    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "权限id", required = true, paramType = "path", dataTypeClass = Integer.class)
    public DataResult delete(@PathVariable("id") Integer id) {
        logger.info("执行menu/delete,参数为:{}",id );
        try {
            menuService.delete(id);
            logger.info("执行menu/delete结束" );
            return DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            logger.error("执行menu/delete结束,错误为:{}" , e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }
    }

    //通过角色id获取相对应的权限
    @GetMapping("getZtreeInfoChecked/{rid}")
    @ApiOperation(value = "通过角色id获取相对应的权限")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataTypeClass = Integer.class)
    public DataResult getZtreeInfoChecked(@PathVariable("rid") Integer rid) {
        logger.info("执行menu/getZtreeInfoChecked,参数为:{}",rid );
        try {
            List<Map<String, Object>> list =  menuService.getZtreeInfoChecked(rid);
            logger.info("执行menu/getZtreeInfoChecked结束" );
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
        } catch (Exception e) {
            logger.error("执行menu/getZtreeInfoChecked结束,错误为:{}" , e);
            return DataResult.response(ResponseStatusEnum.FAIL);
        }

    }

}
