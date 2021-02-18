package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbUserDTO;
import com.dongdongwuliu.domain.vo.TbUserVO;
import com.dongdongwuliu.feign.fallback.TbSiteServiceFeignImplFallBack;
import com.dongdongwuliu.feign.fallback.UserServiceFeignImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Component
@FeignClient(value = "dongdongwuliu-user-service", fallback = UserServiceFeignImplFallBack.class,url = "http://127.0.0.1:8140")
public interface UserServiceFeign {
    //登录
    @GetMapping("/user/login")
    DataResult<Map<String,Object>> login(@RequestParam("username") String username, @RequestParam("password") String password);
    //是否注册
    @GetMapping("/user/registered")
    DataResult<Integer> registered(@RequestParam("username") String username);
    //注册
    @PostMapping("/user/register")
    DataResult register(@RequestBody TbUserDTO tbUserDTO);
//根据用户id 查找数据
    @GetMapping("/user/toUpdate/{key}")
    DataResult<TbUserDTO> getUser(@PathVariable("key") String key);
    //修改
    @PutMapping("/user/update")
    DataResult update(@RequestBody TbUserDTO tbUserDTO);
    //退出登录
    @GetMapping("/user/outLog/{key}")
    DataResult outLog(@PathVariable("key")String key);
    //是否注册
    @GetMapping("/user/changePhone")
    DataResult<Integer> changePhone(@RequestParam("phone") String phone);
}
