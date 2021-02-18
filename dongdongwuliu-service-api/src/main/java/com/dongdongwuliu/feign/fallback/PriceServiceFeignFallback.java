package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.CarriagePriceVO;
import com.dongdongwuliu.domain.vo.CourierPriceVO;
import com.dongdongwuliu.domain.vo.SpecialPriceVO;
import com.dongdongwuliu.feign.PriceServiceFeign;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceServiceFeignFallback implements PriceServiceFeign {

// ------------------------------------普通线路表-------------------------------------------------------

    @Override
    public DataResult<List<CarriagePriceVO>> getCarriagePriceList() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult<CarriagePriceVO> getCarriagePriceByPathId(Long pathId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult<CarriagePriceVO> getCarriagePriceById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult addCarriagePrice(CarriagePriceVO carriagePriceVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult updateCarriagePrice(CarriagePriceVO carriagePriceVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult deleteCarriagePriceById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult deleteCarriagePriceByPathId(Long pathId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }


// -----------------------------------配送价格表--------------------------------------------------------

    @Override
    public DataResult<List<CourierPriceVO>> getCourierPriceList() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult<List<CourierPriceVO>> getCourierPriceBySiteId(Integer siteId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult getCourierPriceBySiteIdAndSiteScope(CourierPriceVO courierPriceVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult<CourierPriceVO> getCourierPriceById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult addCourierPrice(CourierPriceVO courierPriceVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult updateCourierPrice(CourierPriceVO courierPriceVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult deleteCourierPriceById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult deleteCourierPriceBySiteId(Integer siteId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }


// -----------------------------------专线价格表--------------------------------------------------------

    @Override
    public DataResult<List<SpecialPriceVO>> getSpecialPriceList() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult<SpecialPriceVO> getSpecialPriceBySpecialId(Long specialId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult<SpecialPriceVO> getSpecialPriceById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult addSpecialPrice(SpecialPriceVO specialPriceVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult updateSpecialPrice(SpecialPriceVO specialPriceVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult deleteSpecialPriceById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult deleteSpecialPriceBySpecialId(Long specialId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }
}
