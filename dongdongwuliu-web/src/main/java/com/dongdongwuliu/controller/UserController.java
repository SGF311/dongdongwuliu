package com.dongdongwuliu.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbUserDTO;
import com.dongdongwuliu.domain.vo.TbPersonVO;
import com.dongdongwuliu.domain.vo.TbUserVO;
import com.dongdongwuliu.feign.UserServiceFeign;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("user")
@Api(description = "用户登录注册接口")
public class UserController {

    @Autowired
    private UserServiceFeign userServiceFeign;

    //登录
    @RequestMapping("login")
    public String login(String username, String password, Model model, RedirectAttributes attr) {
        DataResult<Map<String,Object>> result = userServiceFeign.login(username,password);
        if(result.getCode()==200){
            Map<String,Object> data =(Map<String,Object>) result.getData();

            String key = (String) data.get("Userkey");
//            //当前登录对象
            String s = (String)data.get("user");

            attr.addAttribute("key", key);//跳转地址带上test参数
            attr.addAttribute("s",s);//跳转地址带上test参数
            return "redirect:/toIndex";
        }else{
            String msg = JSONObject.toJSONString(result.getMessage());
            model.addAttribute("msg",msg);
            return "login";
        }
    }

    //查看是否注册
    @RequestMapping("registered")
    @ResponseBody
    public DataResult registered(String username) {
        DataResult<Integer> result = userServiceFeign.registered(username);
        return result;
    }

    //注册
    @RequestMapping("register")
    @ResponseBody
    public DataResult register(TbUserVO tbUserVO) {
        TbUserDTO tbUserDTO = new TbUserDTO();
        BeanUtils.copyProperties(tbUserVO,tbUserDTO);
        DataResult result = userServiceFeign.register(tbUserDTO);
        return result;
    }

    /** @Description 跳转个人中心
     * @Date 14:14 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("toUser/{key}")
    public String toUser(@PathVariable("key")String Key,Model model) {
        //根据id查询数据
        DataResult<TbUserDTO> result = userServiceFeign.getUser(Key);
        if(result.getCode() == 200){
            TbUserVO tbUserVO = new TbUserVO();
            BeanUtils.copyProperties(result.getData(),tbUserVO);
            model.addAttribute("tbUserVO",tbUserVO);
        }else{
            return "redirect:/tologin";
        }
        return "user";
    }

    //修改
    @RequestMapping("update")
    @ResponseBody
    public DataResult update(TbUserVO tbUserVO) {
        TbUserDTO tbUserDTO = new TbUserDTO();
        BeanUtils.copyProperties(tbUserVO,tbUserDTO);
        DataResult result = userServiceFeign.update(tbUserDTO);
        return result;
    }
    //退出登录
    @RequestMapping("logout/{key}")
    public String logout(@PathVariable("key") String key) {
        DataResult result = userServiceFeign.outLog(key);
        return "index";
    }

    @RequestMapping("changePhone")
    @ResponseBody
    public DataResult changePhone(String phone) {
        DataResult<Integer> result = userServiceFeign.changePhone(phone);
        return result;
    }


}
