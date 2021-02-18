package com.dongdongwuliu.feign;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.domain.vo.CarriagePriceVO;
import com.dongdongwuliu.domain.vo.CourierPriceVO;
import com.dongdongwuliu.domain.vo.SpecialPriceVO;
import com.dongdongwuliu.feign.fallback.PriceServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "dongdongwuliu-price-service", fallback = PriceServiceFeignFallback.class,url = "http://127.0.0.1:8070")
public interface PriceServiceFeign {
// ------------------------------------普通线路表-------------------------------------------------------

    // 查询所有普通线路运输价格
    @GetMapping("/carriagePrice/getCarriagePriceList")
    DataResult<List<CarriagePriceVO>> getCarriagePriceList();

    // 根据普通线路ID查询运输价格
    @GetMapping("/carriagePrice/getCarriagePriceByPathId/{pathId}")
    DataResult<CarriagePriceVO> getCarriagePriceByPathId(@PathVariable("pathId")Long pathId);

    // 根据普通线路价格ID查询运输价格
    @GetMapping("/carriagePrice/{id}")
    DataResult<CarriagePriceVO> getCarriagePriceById(@PathVariable("id")Long id);

    // 增加普通线路运输价格
    @PutMapping("/carriagePrice")
    DataResult addCarriagePrice(@RequestBody CarriagePriceVO carriagePriceVO);

    // 修改普通线路运输价格
    @PutMapping("/carriagePrice/updateCarriagePrice")
    DataResult updateCarriagePrice(@RequestBody CarriagePriceVO carriagePriceVO);

    // 根据普通线路价格ID删除
    @DeleteMapping("/carriagePrice/{id}")
    DataResult deleteCarriagePriceById(@PathVariable("id")Long id);

    // 根据普通线路ID删除
    @DeleteMapping("/carriagePrice/deleteCarriagePriceByPathId/{pathId}")
    DataResult deleteCarriagePriceByPathId(@PathVariable("pathId")Long pathId);


// -----------------------------------配送价格表--------------------------------------------------------

    // 查询所有配送价格
    @GetMapping("/courierPrice/getCourierPriceList")
    DataResult<List<CourierPriceVO>> getCourierPriceList();

    // 根据站点ID查询配送价格
    @GetMapping("/courierPrice/getCourierPriceBySiteId/{siteId}")
    DataResult<List<CourierPriceVO>> getCourierPriceBySiteId(@PathVariable("siteId")Integer siteId);

    // 根据站点ID以及配送距离查询配送价格
    @PostMapping("/courierPrice/getCourierPriceBySiteIdAndSiteScope")
    DataResult getCourierPriceBySiteIdAndSiteScope(@RequestBody CourierPriceVO courierPriceVO);

    // 根据ID查询配送价格
    @GetMapping("/courierPrice/{id}")
    DataResult<CourierPriceVO> getCourierPriceById(@PathVariable("id")Long id);

    // 增加配送价格
    @PutMapping("/courierPrice")
    DataResult addCourierPrice(@RequestBody CourierPriceVO courierPriceVO);

    // 根据ID修改配送价格
    @PutMapping("/courierPrice/updateCourierPrice")
    DataResult updateCourierPrice(@RequestBody CourierPriceVO courierPriceVO);

    // 根据ID删除
    @DeleteMapping("/courierPrice/{id}")
    DataResult deleteCourierPriceById(@PathVariable("id")Long id);

    // 根据站点ID删除
    @DeleteMapping("/courierPrice/deleteCourierPriceBySiteId/{siteId}")
    DataResult deleteCourierPriceBySiteId(@PathVariable("siteId")Integer siteId);


// -----------------------------------专线价格表--------------------------------------------------------

    // 查询所有专线线路运输价格
    @GetMapping("/specialPrice/getSpecialPriceList")
    DataResult<List<SpecialPriceVO>> getSpecialPriceList();

    // 根据专线线路ID查询运输价格
    @GetMapping("/specialPrice/getSpecialPriceBySpecialId/{specialId}")
    DataResult<SpecialPriceVO> getSpecialPriceBySpecialId(@PathVariable("specialId")Long specialId);

    // 根据专线线路价格ID查询运输价格
    @GetMapping("/specialPrice/{id}")
    DataResult<SpecialPriceVO> getSpecialPriceById(@PathVariable("id")Long id);

    // 增加专线线路运输价格
    @PutMapping("/specialPrice")
    DataResult addSpecialPrice(@RequestBody SpecialPriceVO specialPriceVO);

    // 修改专线线路运输价格
    @PutMapping("/specialPrice/updateSpecialPrice")
    DataResult updateSpecialPrice(@RequestBody SpecialPriceVO specialPriceVO);

    // 根据专线价格ID删除
    @DeleteMapping("/specialPrice/{id}")
    DataResult deleteSpecialPriceById(@PathVariable("id")Long id);

    // 根据专线线路ID删除
    @DeleteMapping("/specialPrice/deleteSpecialPriceBySpecialId/{specialId}")
    DataResult deleteSpecialPriceBySpecialId(@PathVariable("specialId")Long specialId);
}
