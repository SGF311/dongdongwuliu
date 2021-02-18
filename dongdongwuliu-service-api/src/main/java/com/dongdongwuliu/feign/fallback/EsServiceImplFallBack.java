package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbOrderDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.feign.EsServiceFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/2/3 20:59
 * @Description:
 */
@Component
public class EsServiceImplFallBack implements EsServiceFeign {

    @Override
    public DataResult selectOrderEs(String phoneOrOrderId,String orderId,String username) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult updateStatusByUsernameByOrderId(String username, String orderId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult<List<TbOrderVO>> getEsOrderByPhoneByOrderId(String senderMobile, String orderId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult<List<TbOrderVO>> getEsOrderInfo(String username) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }

    @Override
    public DataResult addEsByOrder(TbOrderDTO tbOrderDTO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级");
    }
}
