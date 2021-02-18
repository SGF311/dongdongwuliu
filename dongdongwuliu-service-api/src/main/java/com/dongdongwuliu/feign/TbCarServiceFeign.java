package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbCarDTO;
import com.dongdongwuliu.domain.vo.TbCarVO;
import com.dongdongwuliu.feign.fallback.TbCarServiceImplFallBacK;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//   因为Fallback是通过Hystrix实现的， 所以需要开启Hystrix
//   class需要继承当前FeignClient的类
@Component
@FeignClient(value = "dongdongwuliu-car-service", fallback = TbCarServiceImplFallBacK.class,url = "http://127.0.0.1:8040")
public interface TbCarServiceFeign {

    //汽车管理表
    @PostMapping("/carController/selectPage")
    DataResult selectPage(TbCarDTO tbCarDTO,@RequestParam("pageNumber") Integer pageNumber,@RequestParam("pageSize") Integer pageSize);

    //根据id或者是车牌号进行查询
    @PostMapping("/carController/selectByIdOrCarNumber")
    DataResult<TbCarVO> selectByIdOrCarNumber(TbCarDTO tbCarDTO);

    //增加车辆信息
    @PostMapping("/carController/insertInfoCar")
    DataResult insertInfoCar(TbCarVO tbCarVO);

    //修改车辆信息
    @PutMapping("/carController/updateByIdOrCarNumber")
    DataResult updateByIdOrCarNumber(TbCarVO carVO);

    //删除信息
    @DeleteMapping("/carController/deleteByIdOrCarNumber")
    DataResult deleteByIdOrCarNumber(TbCarVO carVO);

    //查询可调度车辆信息
    @PostMapping("/carController/carControlSelect")
    DataResult carControlSelect();
}
