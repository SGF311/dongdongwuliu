package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.dto.TbRoleDTO;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import com.dongdongwuliu.domain.vo.TbRoleVO;
import com.dongdongwuliu.feign.TbPersonServiceFeign;
import com.dongdongwuliu.feign.TbSiteServiceFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("person")
public class TPersonController {

    @Autowired
    private TbPersonServiceFeign tbPersonServiceFeign;

    @Autowired
    private SiteController SiteController;

    @RequestMapping("toperson")
    public String togetInfo() {
        return "admin/person/list";
    }

    //获取用户表的全部信息
    @RequestMapping("getInfo")
    @ResponseBody
    public List<TbPersonVO> getInfo() {
        DataResult result = tbPersonServiceFeign.getInfo();

        List<TbPersonVO> list =(List<TbPersonVO>) result.getData();
        return list;
    }

    //跳转增加页面
    @RequestMapping("toinsert")
    public String toinsert(Model model) {
        //获取所有的角色
        DataResult<List<TbRoleDTO>> rolesDto = tbPersonServiceFeign.getInfoRoles();
        String s = JSONObject.toJSONString(rolesDto.getData());
        List<TbRoleVO> roles = JSONObject.parseObject(s, List.class);
        model.addAttribute("roles", roles);
        //获取所有的站点
//        SiteController.

        return "admin/person/insertPerson";
    }

    //增加用户表和用户角色表
    @RequestMapping("insertInto")
    @ResponseBody
    public Integer insertInto(TbPersonVO tPerson, Integer[] role) {

        DataResult result = tbPersonServiceFeign.insertIntoPerson(tPerson, role);
        if (result.getCode() == 200) {
            return 1;//增加成功
        }
        return 2;//增加失败
    }

    //回显
    @RequestMapping("toupdate/{uid}")
    public String toupdate(@PathVariable("uid") Integer uid, Model model) {
        DataResult<Map<String, Object>> result = tbPersonServiceFeign.toupdate(uid);
        Map<String, Object> data = result.getData();
        model.addAttribute("roles", data.get("roles"));
        model.addAttribute("roleid", data.get("roleid"));
        model.addAttribute("tPerson", data.get("person"));
        return "admin/person/updatePerson";
    }

    //修改
    @RequestMapping("update")
    @ResponseBody
    public Integer update(TbPersonVO tPerson, Integer[] role) {

        DataResult result = tbPersonServiceFeign.updatePerson(tPerson, role);
        if (result.getCode() == 200) {
            return 1;//修改成功
        }
        return 2;//修改失败
    }

    //删除
    @RequestMapping("delete")
    @ResponseBody
    public Integer delete(Integer uid) {
        DataResult result = tbPersonServiceFeign.deletePerson(uid);
        if (result.getCode() == 200) {
            return 1;//删除成功
        }
        return 2;//删除失败
    }
}
