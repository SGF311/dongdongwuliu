package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.MenuDTO;
import com.dongdongwuliu.domain.vo.MenuVO;
import com.dongdongwuliu.feign.TbPersonServiceFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private TbPersonServiceFeign tbPersonServiceFeign;

    //获取菜单
    @RequestMapping("getMenu")
    @ResponseBody
    public List<Map<String, Object>> getMenu(String key) {
        DataResult<List<Map<String, Object>>> dataResult = tbPersonServiceFeign.getMenu(key);

        List<Map<String, Object>> list = dataResult.getData();

        if (dataResult.getCode() == 200) {
            return list;
        } else {
            return null;
        }
    }

    //ztree页面把
    @RequestMapping("toMenu")
    public String togetZtree() {
        return "admin/menu/list";
    }

    //获取ztree
    @RequestMapping("getZtree")
    @ResponseBody
    public List<Map<String, Object>> getZtree() {
        DataResult<List<Map<String, Object>>> result = tbPersonServiceFeign.getZtree();
        return result.getData();
    }

    @RequestMapping("toinsert/{pid}")
    public String toinsert(@PathVariable("pid") Integer pid,Model model) {
        model.addAttribute("pid",pid);
        return "admin/menu/insertIntoMenu";
    }

    //增加权限
    @RequestMapping("insertInTo")
    @ResponseBody
    public Integer insertInTo( MenuVO menu) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menu, menuDTO);
        DataResult result = tbPersonServiceFeign.insertInto(menuDTO);
        if (result.getCode() == 200) {
            return 1;//增加成功
        }
        return 2;//增加失败
    }

    //根据id 查找数据
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Integer id, Model model) {
        DataResult<MenuDTO> result = tbPersonServiceFeign.getInfoById(id);
        MenuVO menu = new MenuVO();
        BeanUtils.copyProperties(result.getData(), menu);
        model.addAttribute("menu", menu);
        return "admin/menu/updateMenu";
    }

    //修改权限表
    @RequestMapping("update")
    @ResponseBody
    public Integer update(MenuVO menu) {

        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menu, menuDTO);
        DataResult result = tbPersonServiceFeign.update(menuDTO);
        if (result.getCode() == 200) {
            return 1;//修改成功
        }
        return 2;//修改失败
    }

    //删除权限
    @RequestMapping("delete")
    @ResponseBody
    public Integer delete(Integer id) {

        DataResult result = tbPersonServiceFeign.delete(id);

        if (result.getCode() == 200) {
            return 1;//修改成功
        }
        return 2;//修改失败
    }

    //获取角色对应的权限
    @RequestMapping("getZtreeInfoChecked/{rid}")
    @ResponseBody
    public List<Map<String, Object>> getZtreeInfoChecked(@PathVariable("rid") Integer rid) {
        DataResult<List<Map<String, Object>>> result = tbPersonServiceFeign.getZtreeInfoChecked(rid);
        List<Map<String, Object>> data = result.getData();
        return data;
    }
}
