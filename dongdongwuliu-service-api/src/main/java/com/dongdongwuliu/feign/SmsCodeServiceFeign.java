package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.vo.TbContentCategoryVO;
import com.dongdongwuliu.domain.vo.TbContentVO;
import com.dongdongwuliu.feign.fallback.ContentServiceImplFallBack;
import com.dongdongwuliu.feign.fallback.SmsCodeServiceImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//   因为Fallback是通过Hystrix实现的， 所以需要开启Hystrix
//   class需要继承当前FeignClient的类
@Component
@FeignClient(value = "dongdongwuliu-sms-code-service", fallback = SmsCodeServiceImplFallBack.class,url = "http://127.0.0.1:8130")
public interface SmsCodeServiceFeign {
    //获取验证码
    @GetMapping("/vs/code/{phone}")
    DataResult getverificationCode(@PathVariable("phone") String phone);
}
