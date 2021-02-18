package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.vo.TbContentCategoryVO;
import com.dongdongwuliu.domain.vo.TbContentVO;
import com.dongdongwuliu.feign.fallback.ContentServiceImplFallBack;
import com.dongdongwuliu.feign.fallback.TbPersonServiceImplFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//   因为Fallback是通过Hystrix实现的， 所以需要开启Hystrix
//   class需要继承当前FeignClient的类
@Component
@FeignClient(value = "dongdongwuliu-advertising-service", fallback = ContentServiceImplFallBack.class,url = "http://127.0.0.1:8030")
public interface ContentServiceFeign {
    //查寻全部 并分页
    @GetMapping("/content/findAll")
    DataResult findAll(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize);

    //图片上传
    @GetMapping("/content/uploadImg")
    DataResult uploadImg(@RequestParam(value = "myFile") MultipartFile myFile);

    //增加
    @PostMapping("/content/insert")
    DataResult insert(@RequestBody TbContentVO tbContent);

    //删除
    @DeleteMapping("/content/deleteById/{id}")
    DataResult deleteById(@PathVariable("id") Long id);

    //多删
    @DeleteMapping("/content/deleteBatch/{ids}")
    DataResult deleteBatch(@PathVariable("ids")Long[] ids);
    //根据id查询
    @GetMapping("/content/toUpdate/{id}")
    DataResult toUpdate(@PathVariable("id") Long id);
    //修改
    @PutMapping("/content/update")
    DataResult update(@RequestBody TbContentVO tbContent);
    //上下移动
    @PostMapping("/content/upDown")
    DataResult upDown(@RequestParam("id") Long id, @RequestParam("id")Integer flag);

    //获取页面广告
    @GetMapping("/content/getContentByContentCategoryId/{contentCategoryId}")
    DataResult<List<TbContentVO>> getContentByContentCategoryId(@PathVariable("contentCategoryId") Long contentCategoryId);
//--------------------------内容------------------------
    //查找全部
    @GetMapping("/contentCategory/findAll")
    DataResult findAllContentCategory();
    //增加数据
    @PostMapping("/contentCategory/insert")
    DataResult insertTbContentCategory(@RequestBody TbContentCategoryVO tbContentCategoryVO);
//删除
    @DeleteMapping("/contentCategory/deleteById/{id}")
    DataResult deleteByIdTbContentCategory(@PathVariable("id") Long id);
    //多删
    @DeleteMapping("/contentCategory/deleteBatch/{ids}")
    DataResult deleteBatchTbContentCategory(@PathVariable("ids")Long[] ids);
    //根据id查询
    @GetMapping("/contentCategory/toUpdate/{id}")
    DataResult toUpdateTbContentCategory(@PathVariable("id") Long id);
    //修改
    @PutMapping("/contentCategory/update")
    DataResult updateTbContentCategory(@RequestBody TbContentCategoryVO tbContentCategoryVO);

}
