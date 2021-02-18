package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.feign.SmsCodeServiceFeign;
import com.dongdongwuliu.feign.TbPersonServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/2/3 17:26
 * @Version 1.0
 **/
@Controller
@Api(description = "验证码接口")
@RefreshScope //开启自动刷新配置
@RequestMapping("/code")
public class CodeController {
    private Logger logger = LoggerFactory.getLogger(CodeController.class);
    //smsCodeServiceFeign
    @Autowired
    private SmsCodeServiceFeign smsCodeServiceFeign;
    /** @Description 获取验证码
     * @Date 17:30 2021/2/3
     * @Param [phone]
     * @return com.dongdongwuliu.data.DataResult
     **/
    @ApiOperation(value = "根据手机号发送验证码")
    @ApiImplicitParam(name = "phone", value = "phone", required = true, paramType = "path", dataTypeClass = String.class)
    @GetMapping("/{phone}")
    @ResponseBody
    public DataResult getverificationCode(@PathVariable("phone")String phone) {
        DataResult dataResult = null;
        try {
            dataResult = smsCodeServiceFeign.getverificationCode(phone);
            dataResult = DataResult.response(ResponseStatusEnum.SUCCESS);
        } catch (Exception e) {
            logger.error("方法执行异常 : {}", e);
            dataResult = DataResult.response(ResponseStatusEnum.BAD_REQUEST);
        }

        return dataResult;
    }
}
