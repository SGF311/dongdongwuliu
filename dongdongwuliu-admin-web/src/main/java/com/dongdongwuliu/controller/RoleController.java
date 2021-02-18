package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbRoleDTO;
import com.dongdongwuliu.domain.vo.TbRoleVO;
import com.dongdongwuliu.feign.TbPersonServiceFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {


    @Autowired
    private TbPersonServiceFeign tbPersonServiceFeign;


    @RequestMapping("toRole")
    public String togetInfo() {
        return "admin/role/list";
    }

    //获取全部的角色
    @RequestMapping("getInfo")
    @ResponseBody
    public List<TbRoleVO> getInfo() {
        DataResult result = tbPersonServiceFeign.getInfoRoles();
        List<TbRoleVO> list = (List<TbRoleVO>)result.getData();
        return list;
    }

    @RequestMapping("toinsert")
    public String toinsert() {
        return "admin/role/insertRole";
    }

    //增加角色
    @RequestMapping("insertInTo")
    @ResponseBody
    public Integer insertInTo(TbRoleDTO role, Integer[] ids) {

        DataResult result = tbPersonServiceFeign.insertInTo(role, ids);
        if (result.getCode() == 200) {
            return 1;
        }
        return 2;
    }

    //回显
    @RequestMapping("toupdate/{id}")
    public String toupdate(@PathVariable("id") Integer id, Model model) {
        //获取id 对应的角色
        DataResult<TbRoleVO> result = tbPersonServiceFeign.getInfoRoleById(id);
        model.addAttribute("role", result.getData());
        return "admin/role/updateRole";
    }

    //修改
    @RequestMapping("update")
    @ResponseBody
    public Integer update(TbRoleDTO role, Integer[] ids) {

        DataResult result = tbPersonServiceFeign.updateRole(role, ids);
        if (result.getCode() == 200) {
            return 1;
        }
        return 2;
    }

    //删除
    @RequestMapping("del")
    @ResponseBody
    public Integer del(Integer id) {

        DataResult result = tbPersonServiceFeign.del(id);
        if (result.getCode() == 200) {
            return 1;
        }
        return 2;
    }
}
