package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.dto.TbPathDTO;
import com.dongdongwuliu.domain.vo.TbPathVO;
import com.dongdongwuliu.domain.vo.TbSpecialVO;
import com.dongdongwuliu.feign.fallback.PathServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "dongdongwuliu-path-service", fallback = PathServiceFeignFallback.class,url = "http://127.0.0.1:8060")
public interface PathServiceFeign {


    //查询
    @GetMapping("/pathController/selectList")
    DataResult<List<TbPathDTO>> selectList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "2") Integer pageSize);

    //增加
    @PostMapping("/pathController/insertInfoPath")
    DataResult insertInfoPath(@RequestBody TbPathVO tbPathVO);


    //回回显
    @GetMapping("/pathController/selectInfoById/{id}")
    DataResult<TbPathVO> selectInfoById(@PathVariable("id")Long id);


    //线路修改,根据实体修改线路
    @PutMapping("/pathController/updatePathInfo")
    DataResult updatePathInfo(TbPathVO tbPathVO);


    //根据id删除
    @DeleteMapping("/pathController/deleteByPath/{id}")
    DataResult deleteByPath(@PathVariable("id")Long id);


    //根据寄件人省编号  收件人省编号 查询线路
    @GetMapping("/pathController/selectStartpidByInfo/{startPid}/{endPid}")
    DataResult selectStartpidByInfo(@PathVariable("startPid")Integer startPid,@PathVariable("endPid")Integer endPid);

    //----------------------------------------专线-----------------------------------------------------


    //查询
    @GetMapping("/specisl/getInfo")
    DataResult<List<TbSpecialVO>> getInfo();



    //增加
    @PostMapping("/specisl/addSpecial")
    DataResult addSpecial(@RequestBody TbSpecialVO tbSpecialVO);


    //回显
    @PostMapping("/specisl/selectById/{id}")
    DataResult selectById(@PathVariable("id")Long id);


    //修改
    @PutMapping("/specisl/updateBySpecial")
    DataResult updateBySpecial(@RequestBody TbSpecialVO tbSpecialVO);


    //删除
    @DeleteMapping("/specisl/deleteById/{id}")
    DataResult deleteById(@PathVariable("id")Long id);

}
