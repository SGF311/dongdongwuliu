package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.feign.TbOrderServiceFeign;
import org.springframework.stereotype.Component;

/**
 * @Auther: 你哥
 * @Date: 2021/2/1 23:27
 * @Description:
 */
@Component
public class TbOrderServiceImplFallBack implements TbOrderServiceFeign {

    @Override
    public DataResult updateStatus(String username, String orderId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult fastAddOrderInfo(TbOrderVO tbOrderVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult cancelOrderByOrderId(Long orderId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult deleteOrder(Long orderId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult findOrderByOutTradeNoBySenderMobileByStatus(TbOrderVO tbOrderVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult updateOrderInfo(TbOrderVO tbOrderVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult findOrderByOrderId(String orderId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult selectVisitTime() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult addOrderInfo(TbOrderVO tbOrderVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult getCargoTypeInfo() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult getInfo() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult getSms(String phone) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

}
