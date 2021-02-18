package com.dongdongwuliu.controller;


import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbPersonDTO;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import com.dongdongwuliu.feign.TbPersonServiceFeign;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2021/1/30 10:00
 * @Version 1.0
 **/
@Controller
@Api(description = "登录接口")
@RefreshScope //开启自动刷新配置
public class LoginController {

    //    //tperson的fegin
    @Autowired
    private TbPersonServiceFeign tbPersonServiceFeign;


    @RequestMapping({"tologin","/"})
    public String tologin() {
        System.out.println("跳转登录");
        return "login";
    }

    //调登录接口
    @RequestMapping("login")
    public String login(TbPersonDTO tbPersonDTO, Model model) {
        TbPersonVO tbPersonVO1 = new TbPersonVO();
        BeanUtils.copyProperties(tbPersonDTO,tbPersonVO1);
        DataResult result = tbPersonServiceFeign.login(tbPersonVO1);
        if (result.getCode() == 200) {
            Map<String,Object> data =(Map<String,Object>) result.getData();
            String key = (String) data.get("key");
            //把登录态带到前端
            model.addAttribute("key", key);
//            //当前登录对象
            Object tbPersonDTO1 = data.get("TbPersonDTO");
            TbPersonVO tbPersonVO = JSONObject.parseObject(tbPersonDTO1.toString(), TbPersonVO.class);
            model.addAttribute("user", tbPersonVO);
            return "admin/index";
        } else {
            model.addAttribute("result", result);
            return "admin/error";
        }
    }

    @RequestMapping("logout/{key}")
    public String logout(@PathVariable("key") String key) {
        DataResult result = tbPersonServiceFeign.outLog(key);
        return "redirect:/tologin";
    }
}
