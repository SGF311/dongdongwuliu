package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbSiteDTO;
import com.dongdongwuliu.domain.vo.TbSiteVO;
import com.dongdongwuliu.feign.TbSiteServiceFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Deacription TODO
 * @Author wkk
 * @Date 2021/2/2 17:11
 * @Version 1.0
 **/
@Component
public class TbSiteServiceFeignImplFallBack implements TbSiteServiceFeign {

    @Override
    public DataResult selectByIdOrSiteName(TbSiteDTO tbSiteDTO, Integer pageNumber, Integer pageSize) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,展示所有及分页");
    }

    @Override
    public DataResult addSiteInfo(TbSiteDTO tbSiteDTO) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,增加失败");
    }

    @Override
    public DataResult toUpdate(Integer siteId) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,查询失败");
    }

    @Override
    public DataResult updateInfoSite(TbSiteVO tbSiteVO) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,修改失败");
    }

    @Override
    public DataResult deleteBySiteId(Integer siteId) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,删除失败");
    }

    @Override
    public DataResult selectAll() {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,查询失败");
    }

    @Override
    public DataResult selectByCid(String cid) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,查询失败");
    }

    @Override
    public DataResult selectByAid(String aid) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,查询失败");
    }

    @Override
    public DataResult webSelectAll(Integer pageNum, Integer pageSize) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,查询失败");
    }

    @Override
    public DataResult searchSite(TbSiteVO tbSiteVO) {
        return  DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,查询失败");
    }
}