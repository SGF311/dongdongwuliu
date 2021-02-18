package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.MenuDTO;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.dto.TbRoleDTO;
import com.dongdongwuliu.domain.vo.MenuVO;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import com.dongdongwuliu.domain.vo.TbRoleVO;
import com.dongdongwuliu.feign.fallback.TbPersonServiceImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//   因为Fallback是通过Hystrix实现的， 所以需要开启Hystrix
//   class需要继承当前FeignClient的类
@Component
@FeignClient(value = "dongdongwuliu-person-service", fallback = TbPersonServiceImplFallBack.class,url = "http://127.0.0.1:8010")
public interface TbPersonServiceFeign {



    @PostMapping("/tbPerson/login")
    DataResult login(@RequestBody TbPersonVO tbPersonVO);

    //获取菜单
    @GetMapping("/menu/getMenu")
    DataResult<List<Map<String, Object>>> getMenu(@RequestParam("key")String key);

    //获取当前登录用户
    @GetMapping("/tbPerson/getUid/{key}")
    DataResult<TbPersonDTO> getUid(@PathVariable("key") String key);

    //获取ztree
    @GetMapping("/menu/getZtree")
    DataResult<List<Map<String, Object>>> getZtree();

    //增加权限表数据
    @PostMapping("/menu/insertInTo")
    DataResult insertInto(@RequestBody MenuDTO menu);

    //根据id查找权限数据
    @GetMapping("/menu/toUpdate/{mid}")
    DataResult<MenuDTO> getInfoById(@PathVariable("mid") Integer mid);

    //修改权限表
    @PutMapping("/menu/update")
    DataResult update(@RequestBody MenuDTO menuDTO);

    //删除权限表数据
    @DeleteMapping("/menu/delete/{id}")
    DataResult delete(@PathVariable("id") Integer id);

    //获取角色对应的权限
    @GetMapping("/menu/getZtreeInfoChecked/{rid}")
    DataResult<List<Map<String, Object>>> getZtreeInfoChecked(@PathVariable("rid") Integer rid);


//----------------------------------用户表-------------------------------------------------------------

    //获取用户表的全部数据
    @GetMapping("/tbPerson/findAll")
    DataResult getInfo();

    //增加用户表和用户角色表
    @PostMapping("/tbPerson/insertInto")
    DataResult insertIntoPerson(@RequestBody TbPersonVO tPerson, @RequestParam Integer[] roles);

    //通过id查询数据
    @GetMapping("/tbPerson/toupdate/{uid}")
    DataResult<Map<String, Object>> toupdate(@PathVariable("uid") Integer uid);

    //修改
    @PutMapping("/tbPerson/update")
    DataResult updatePerson(@RequestBody TbPersonVO tPerson, @RequestParam Integer[] role);

    //删除
    @DeleteMapping("/tbPerson/delete/{uid}")
    DataResult deletePerson(@PathVariable("uid") Integer uid);

    //----------------------------------角色表---------------------------------------------------------------
    //获取所有的角色表数据
    @GetMapping("/role/getInfo")
    DataResult getInfoRoles();

    //增加角色表数据 和 角色权限表
    @PostMapping("/role/insertInto")
    DataResult insertInTo(@RequestBody TbRoleDTO role, @RequestParam Integer[] ids);

    //根据id查数据
    @GetMapping("/role/toUpdate/{rid}")
    DataResult<TbRoleVO> getInfoRoleById(@PathVariable("rid") Integer rid);

    //修改
    @PutMapping("/role/update")
    DataResult updateRole(@RequestBody TbRoleDTO role, @RequestParam Integer[] ids);

    //删除
    @DeleteMapping("/role/delete/{id}")
    DataResult del(@PathVariable("id") Integer id);
    //删除
    @GetMapping("/tbPerson/outLog/{key}")
    DataResult outLog(@PathVariable("key") String key);
    //根据typeid查询
    @GetMapping("/tbPerson/selectByType/{type}/{sid}")
    DataResult<List<TbPersonVO>> selectByType(@PathVariable("type")Integer type,@PathVariable("sid")Integer sid);
}
