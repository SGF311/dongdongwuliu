package com.dongdongwuliu.feign.fallback;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.vo.TbContentCategoryVO;
import com.dongdongwuliu.domain.vo.TbContentVO;
import com.dongdongwuliu.feign.ContentServiceFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ContentServiceImplFallBack implements ContentServiceFeign {
    @Override
    public DataResult findAll(Integer pageNum, Integer pageSize) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult uploadImg(MultipartFile myFile) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult insert(TbContentVO tbContent) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult deleteById(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult deleteBatch(Long[] ids) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult toUpdate(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult update(TbContentVO tbContent) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult upDown(Long id, Integer flag) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult findAllContentCategory() {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult insertTbContentCategory(TbContentCategoryVO tbContentCategoryVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult deleteByIdTbContentCategory(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult deleteBatchTbContentCategory(Long[] ids) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult toUpdateTbContentCategory(Long id) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult updateTbContentCategory(TbContentCategoryVO tbContentCategoryVO) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }

    @Override
    public DataResult<List<TbContentVO>> getContentByContentCategoryId(Long contentCategoryId) {
        return DataResult.response(ResponseStatusEnum.FAIL).setData("服务降级,网络太差,太挤了");
    }
}
