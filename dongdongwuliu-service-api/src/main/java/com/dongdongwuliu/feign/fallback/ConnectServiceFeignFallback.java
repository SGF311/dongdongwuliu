package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.ConnectVO;
import com.dongdongwuliu.feign.ConnectServiceFeign;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConnectServiceFeignFallback implements ConnectServiceFeign {
    @Override
    public DataResult<List<ConnectVO>> getConnectList() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult<List<ConnectVO>> getConnectByOrderId(String orderId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult<ConnectVO> getConnectById(String id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult addConnect(ConnectVO connectVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult updateConnect(ConnectVO connectVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult deleteConnectById(String id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }

    @Override
    public DataResult deleteConnectByOrderId(String orderId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("网络太差,请刷新重试");
    }
}
