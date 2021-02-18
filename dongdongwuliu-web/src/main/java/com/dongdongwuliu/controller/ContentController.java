package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.vo.TbContentCategoryVO;
import com.dongdongwuliu.domain.vo.TbContentVO;
import com.dongdongwuliu.feign.ContentServiceFeign;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("content")
@Api(description = "广告接口")
@RefreshScope //开启自动刷新配置
public class ContentController {
    @Autowired
    private ContentServiceFeign contentServiceFeign;

    @RequestMapping("getContentByContentCategoryId")
    @ResponseBody
    public DataResult<List<TbContentVO>> getContentByContentCategoryId(@RequestParam("contentCategoryId")Long contentCategoryId){

        DataResult<List<TbContentVO>> listDataResult = contentServiceFeign.getContentByContentCategoryId(contentCategoryId);
        return listDataResult;
    }

}
