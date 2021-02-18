package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.vo.TbContentCategoryVO;
import com.dongdongwuliu.feign.ContentServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("contentCategory")
public class ContentCategoryController {
    @Autowired
    private ContentServiceFeign contentServiceFeign;


    //跳转广告列表
    @RequestMapping("toContentCategory")
    public String toContent(){
        return "admin/content_category/list";
    }

    //获取全部广告页数据
    @RequestMapping("findAll")
    @ResponseBody
    public List<TbContentCategoryVO> findAll(){
        DataResult result =  contentServiceFeign.findAllContentCategory();
        String s = JSONObject.toJSONString(result.getData());
        List<TbContentCategoryVO> list = JSONObject.parseObject(s, List.class);
        return list;
    }

    @RequestMapping("toInsert")
    public String  toInsert(){
        return "admin/content_category/insert";
    }

    //增加 insert
    @RequestMapping("insert")
    @ResponseBody
    public Integer insert(TbContentCategoryVO tbContentCategoryVO){
        DataResult result =  contentServiceFeign.insertTbContentCategory(tbContentCategoryVO);
        if(result.getCode() == 200){
            return 1;
        }
        return 2;
    }

    //删除 deleteById
    @RequestMapping("deleteById/{id}")
    @ResponseBody
    public Integer deleteById(@PathVariable("id")Long id){
        DataResult result =  contentServiceFeign.deleteByIdTbContentCategory(id);
        if(result.getCode() == 200){
            return 1;
        }
        return 2;
    }

    //删除 deleteById
    @RequestMapping("deleteBatch/{ids}")
    @ResponseBody
    public Integer deleteBatch(@PathVariable("ids")Long[] ids){
        DataResult result =  contentServiceFeign.deleteBatchTbContentCategory(ids);
        if(result.getCode() == 200){
            return 1;
        }
        return 2;
    }

    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable("id")Long id, Model model){
        DataResult result = contentServiceFeign.toUpdateTbContentCategory(id);
        String s = JSONObject.toJSONString(result.getData());
        TbContentCategoryVO tbContentCategoryVO = JSONObject.parseObject(s, TbContentCategoryVO.class);
        model.addAttribute("tbContentCategoryVO",tbContentCategoryVO);
        return "admin/content_category/update";
    }

    //xiugai  update

    @RequestMapping("update")
    @ResponseBody
    public Integer update(TbContentCategoryVO tbContentCategoryVO){
        DataResult result =  contentServiceFeign.updateTbContentCategory(tbContentCategoryVO);
        if(result.getCode() == 200){
            return 1;
        }
        return 2;
    }
}
