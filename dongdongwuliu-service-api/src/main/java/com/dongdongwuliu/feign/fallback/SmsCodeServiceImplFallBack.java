package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.TbContentCategoryVO;
import com.dongdongwuliu.domain.vo.TbContentVO;
import com.dongdongwuliu.feign.ContentServiceFeign;
import com.dongdongwuliu.feign.SmsCodeServiceFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SmsCodeServiceImplFallBack implements SmsCodeServiceFeign {
    //获取验证码
    @Override
    public DataResult getverificationCode(String phone) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("短信调用失败，服务降级");
    }
}
