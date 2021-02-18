package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbContentCategoryDTO;
import com.dongdongwuliu.domain.vo.TbContentCategoryVO;
import com.dongdongwuliu.domain.vo.TbContentVO;
import com.dongdongwuliu.feign.ContentServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("content")
@Api(description = "广告接口")
@RefreshScope //开启自动刷新配置
public class ContentController {
    @Autowired
    private ContentServiceFeign contentServiceFeign;

    //跳转广告列表
    @RequestMapping("toContent")
    public String toContent(Model model){
        //去查内容分类表
        DataResult<List<TbContentCategoryDTO>> result =  contentServiceFeign.findAllContentCategory();
        String s = JSONObject.toJSONString(result.getData());
        List<TbContentCategoryVO> list = JSONObject.parseObject(s, List.class);
        model.addAttribute("list",list);
        return "admin/content/list";
    }
    //获取全部广告页数据
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String,Object>  findAll(@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "2")Integer pageSize){
       DataResult result =  contentServiceFeign.findAll(pageNumber,pageSize);
        String s = JSONObject.toJSONString(result.getData());
        Map map = JSONObject.parseObject(s, Map.class);
        return map;
    }
    //跳转增加页面
    @RequestMapping("toInsert")
    public String toInsert(Model model){
        //去查内容分类表
        DataResult result =  contentServiceFeign.findAllContentCategory();
        String s = JSONObject.toJSONString(result.getData());
        List<TbContentCategoryVO> list = JSONObject.parseObject(s, List.class);
        model.addAttribute("list",list);
        return "admin/content/insert";
    }


//增加
    @RequestMapping("insert")
    @ResponseBody
    public Integer insert(TbContentVO tbContent)  {
        DataResult result = contentServiceFeign.insert(tbContent);
        if(result.getCode() == 200){
            return 1;
        }else{
            return 2;
        }

    }
    //删除
    @RequestMapping("deleteById/{id}")
    @ResponseBody
    public Integer deleteById(@PathVariable("id") Long id){
        DataResult result = contentServiceFeign.deleteById(id);
        if(result.getCode() == 200){
            return 1;
        }else{
            return 2;
        }
    }

    //批量删除
    @RequestMapping("deleteBatch/{ids}")
    @ResponseBody
    public Integer deleteBatch(@PathVariable("ids") Long[] ids){
        DataResult result = contentServiceFeign.deleteBatch(ids);
        if(result.getCode() == 200){
            return 1;
        }else{
            return 2;
        }

    }
    //根据id查询数据 toUpdate
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Long id, Model model){
        DataResult result = contentServiceFeign.toUpdate(id);
        DataResult result2 =  contentServiceFeign.findAllContentCategory();

        if(result.getCode() == 200 && result2.getCode() == 200){
            String s = JSONObject.toJSONString(result.getData());
            TbContentVO tbContentVO = JSONObject.parseObject(s, TbContentVO.class);
            model.addAttribute("tbContentVO",tbContentVO);

            //查询内容明细表
            String s2 = JSONObject.toJSONString(result2.getData());
            List<TbContentCategoryVO> list = JSONObject.parseObject(s2, List.class);
            model.addAttribute("list",list);

            return "admin/content/update";
        }
        return "error";
    }

    //修改  update
    @RequestMapping("update")
    @ResponseBody
    public Integer update(TbContentVO tbContent)  {
        DataResult result = contentServiceFeign.update(tbContent);
        if(result.getCode() == 200){
            return 1;
        }else{
            return 2;
        }
    }

    //上下移动
    @RequestMapping("upDown")
    @ResponseBody
    public Integer upDown(Long id,Integer flag){
        DataResult result = contentServiceFeign.upDown(id,flag);
        if(result.getCode() == 200){
            return 1;
        }else{
            return 2;
        }
    }

}
