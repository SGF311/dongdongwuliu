package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbPathDTO;
import com.dongdongwuliu.domain.vo.*;
import com.dongdongwuliu.feign.PathServiceFeign;
import com.dongdongwuliu.feign.PriceServiceFeign;
import com.dongdongwuliu.feign.TbSiteServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("webPrice")
public class WebPriceController {
    @Resource
    private PriceServiceFeign priceServiceFeign;
    @Resource
    private PathServiceFeign pathServiceFeign;
    @Resource
    private TbSiteServiceFeign siteServiceFeign;

    private Logger logger = LoggerFactory.getLogger(WebPriceController.class);

// ------------------------------------普通线路表-------------------------------------------------------

    // 跳转到普通线路展示页面
    @RequestMapping("toCarriagePrice")
    public String toCarriagePrice(){
        return "admin/carriagePrice/list";
    }

    // 跳转到普通线路增加页面
    @RequestMapping("toAdd")
    public String toAdd(Model model){
        DataResult<List<TbPathDTO>> dataResult = pathServiceFeign.selectList(1, 20);
        model.addAttribute("pathList", dataResult.getData());
        return "admin/carriagePrice/insert";
    }

    // 查询所有普通线路运输价格
    @RequestMapping("getCarriagePriceList")
    @ResponseBody
    public DataResult getCarriagePriceList(){
        try {
            DataResult<List<CarriagePriceVO>> dataResult = priceServiceFeign.getCarriagePriceList();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据普通线路价格ID查询运输价格, 并跳转到修改页面
    @RequestMapping("getCarriagePriceById")
    public String getCarriagePriceById(Long id, Model model){
        DataResult<CarriagePriceVO> dataResult = priceServiceFeign.getCarriagePriceById(id);
        model.addAttribute("carriagePrice", dataResult.getData());
        DataResult<List<TbPathDTO>> listDataResult = pathServiceFeign.selectList(1, 20);
        model.addAttribute("pathList", listDataResult.getData());
       return "admin/carriagePrice/update";
    }

    // 增加普通线路运输价格
    @RequestMapping("addCarriagePrice")
    @ResponseBody
    public DataResult addCarriagePrice(CarriagePriceVO carriagePriceVO){
        try {
            DataResult dataResult = priceServiceFeign.addCarriagePrice(carriagePriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 修改普通线路运输价格
    @RequestMapping("updateCarriagePrice")
    @ResponseBody
    public DataResult updateCarriagePrice(CarriagePriceVO carriagePriceVO){
        try {
            DataResult dataResult = priceServiceFeign.updateCarriagePrice(carriagePriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据普通线路价格ID删除
    @RequestMapping("deleteCarriagePriceById")
    @ResponseBody
    public DataResult deleteCarriagePriceById(Long id){
        try {
            DataResult dataResult = priceServiceFeign.deleteCarriagePriceById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

// -----------------------------------配送价格表--------------------------------------------------------

    // 跳转到配送价格展示页面
    @RequestMapping("toCourierPrice")
    public String toCourierPrice(){
        return "admin/courierPrice/list";
    }

    // 跳转到配送价格增加页面
    @RequestMapping("toAddCourierPrice")
    public String toAddCourierPrice(Model model){
        DataResult<List<TbSiteVO>> dataResult = siteServiceFeign.selectAll();
        model.addAttribute("list", dataResult.getData());
        return "admin/courierPrice/insert";
    }

    // 查询所有配送价格
    @RequestMapping("getCourierPriceList")
    @ResponseBody
    public DataResult getCourierPriceList(){
        try {
            DataResult<List<CourierPriceVO>> dataResult = priceServiceFeign.getCourierPriceList();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID查询配送价格, 并跳转到修改页面
    @RequestMapping("getCourierPriceById")
    public String getCourierPriceById(Long id, Model model){
        DataResult<CourierPriceVO> dataResult = priceServiceFeign.getCourierPriceById(id);
        model.addAttribute("courierPrice", dataResult.getData());
        DataResult<List<TbSiteVO>> listDataResult = siteServiceFeign.selectAll();
        model.addAttribute("list", listDataResult.getData());
        return "admin/courierPrice/update";
    }

    // 增加配送价格
    @RequestMapping("addCourierPrice")
    @ResponseBody
    public DataResult addCourierPrice(CourierPriceVO courierPriceVO){
        try {
            DataResult dataResult = priceServiceFeign.addCourierPrice(courierPriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID修改配送价格
    @RequestMapping("updateCourierPrice")
    @ResponseBody
    public DataResult updateCourierPrice(CourierPriceVO courierPriceVO){
        try {
            DataResult dataResult = priceServiceFeign.updateCourierPrice(courierPriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID删除
    @RequestMapping("deleteCourierPriceById")
    @ResponseBody
    public DataResult deleteCourierPriceById(Long id){
        try {
            DataResult dataResult = priceServiceFeign.deleteCourierPriceById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

// -----------------------------------专线价格表--------------------------------------------------------

    // 跳转到专线线路运输价格展示页面
    @RequestMapping("toSpecialPrice")
    public String toSpecialPrice(){
        return "admin/specialPrice/list";
    }

    // 跳转到专线线路运输价格增加页面
    @RequestMapping("toAddSpecialPrice")
    public String toAddSpecialPrice(Model model){
        DataResult<List<TbSpecialVO>> dataResult = pathServiceFeign.getInfo();
        model.addAttribute("list",dataResult.getData());
        return "admin/specialPrice/insert";
    }

    // 查询所有专线线路运输价格
    @RequestMapping("getSpecialPriceList")
    @ResponseBody
    public DataResult getSpecialPriceList(){
        try {
            DataResult<List<SpecialPriceVO>> dataResult = priceServiceFeign.getSpecialPriceList();
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据专线线路价格ID查询运输价格, 并跳转到修改页面
    @RequestMapping("getSpecialPriceById")
    public String getSpecialPriceById(Long id, Model model){
        DataResult<SpecialPriceVO> dataResult = priceServiceFeign.getSpecialPriceById(id);
        model.addAttribute("specialPrice", dataResult.getData());
        DataResult<List<TbSpecialVO>> listDataResult = pathServiceFeign.getInfo();
        model.addAttribute("list",listDataResult.getData());
        return "admin/specialPrice/update";
    }

    // 增加专线线路运输价格
    @RequestMapping("addSpecialPrice")
    @ResponseBody
    public DataResult addSpecialPrice(SpecialPriceVO specialPriceVO){
        try {
            DataResult dataResult = priceServiceFeign.addSpecialPrice(specialPriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID修改专线线路运输价格
    @RequestMapping("updateSpecialPrice")
    @ResponseBody
    public DataResult updateSpecialPrice(SpecialPriceVO specialPriceVO){
        try {
            DataResult dataResult = priceServiceFeign.updateSpecialPrice(specialPriceVO);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据专线价格ID删除
    @RequestMapping("deleteSpecialPriceById")
    @ResponseBody
    public DataResult deleteSpecialPriceById(Long id){
        try {
            DataResult dataResult = priceServiceFeign.deleteSpecialPriceById(id);
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(dataResult.getData());
        }catch (Exception e){
            logger.error("方法执行异常 : {}", e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
