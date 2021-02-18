package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.pojo.TbPerson;
import com.dongdongwuliu.service.TbPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/29 21:39
 * @Version 1.0
 **/
@Controller
@RestController
@RequestMapping("tbPerson")
@Api(description = "用户接口")
@RefreshScope //开启自动刷新配置
public class TbPersonController {
    @Autowired
    private TbPersonService tbPersonService;

    //    @RequestMapping(value = "/id", method = RequestMethod.GET)
    //body 不可以用于 get
    @ApiOperation(value = "根据id查看订单")
    @ApiImplicitParam(name = "tbPerson", value = "实体类", required = true, paramType = "body", dataTypeClass = TbPerson.class)
    @PostMapping("/selectById")
    public DataResult selectById(@RequestBody TbPerson tbPerson) {
        TbPerson tbPerson1 = tbPersonService.selectById(tbPerson.getUid());
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbPerson1);
    }


    @ApiOperation(value = "根据id查看订单")
    @ApiImplicitParam(name = "uid", value = "uid", required = true, paramType = "path", dataTypeClass = Integer.class)
    @GetMapping("/selectById/{uid}")
    public DataResult selectById(@PathVariable("uid") Integer uid) {
        TbPerson tbPerson1 = tbPersonService.selectById(uid);
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbPerson1);
    }

    @ApiOperation(value = "根据id查看订单")
    @ApiImplicitParam(name = "uid", value = "uid", required = true, paramType = "query", dataTypeClass = Integer.class)
    @GetMapping("/selectByIds")
    public DataResult selectByIds(@RequestParam Integer uid) {
        TbPerson tbPerson1 = tbPersonService.selectById(uid);
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(tbPerson1);
    }
}
