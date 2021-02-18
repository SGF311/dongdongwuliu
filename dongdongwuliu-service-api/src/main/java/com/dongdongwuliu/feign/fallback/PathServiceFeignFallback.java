package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.TbPathVO;
import com.dongdongwuliu.domain.vo.TbSpecialVO;
import com.dongdongwuliu.feign.PathServiceFeign;
import org.springframework.stereotype.Component;

/**
 * @ClassName PathServiceFeignFallback
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/3 0:03
 * @Version 1.0
 **/
@Component
public class PathServiceFeignFallback implements PathServiceFeign {
    @Override
    public DataResult selectList(Integer pageNum, Integer pageSize) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult insertInfoPath(TbPathVO tbPathVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult<TbPathVO> selectInfoById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult updatePathInfo(TbPathVO tbPathVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult deleteByPath(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult getInfo() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult addSpecial(TbSpecialVO tbSpecialVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult selectById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult updateBySpecial(TbSpecialVO tbSpecialVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult deleteById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult selectStartpidByInfo(Integer startPid, Integer endPid) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }
}

 

