package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import com.dongdongwuliu.feign.TbPersonServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("person")
@Api(description = "person用户接口")
public class PersonController {
    @Autowired
    private TbPersonServiceFeign tbPersonServiceFeign;

    //根据站点id查询
    //根据站点id  type id
    @RequestMapping("selectByType/{type}/{sid}")
    @ResponseBody
    public DataResult selectByType(@PathVariable("type") Integer type,@PathVariable("sid") Integer sid){
        try {
           DataResult<List<TbPersonVO>> list =  tbPersonServiceFeign.selectByType(type,sid);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(list);
        } catch (Exception e) {
            return  DataResult.response(ResponseStatusEnum.FAIL);
        }

    }
}
