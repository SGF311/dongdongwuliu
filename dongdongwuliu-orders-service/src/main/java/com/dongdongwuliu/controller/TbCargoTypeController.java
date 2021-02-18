package com.dongdongwuliu.controller;

import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import com.dongdongwuliu.domain.dto.TbCargoTypeDTO;
import com.dongdongwuliu.domain.dto.TbCourierDTO;
import com.dongdongwuliu.domain.dto.TbOrderDetailDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.service.TbCargoTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 你哥
 * @Date: 2021/2/3 10:03
 * @Description:
 */
@RestController
@RequestMapping("cargotype")
@Api(description = "货物类型")
@RefreshScope //开启自动刷新配置
public class TbCargoTypeController {

    private static final Logger logger = LoggerFactory.getLogger(TbCargoTypeController.class);

    @Autowired
    private TbCargoTypeService tbCargoTypeService;

    @ApiOperation(value = "查询货物类型")
    @GetMapping("getCargoTypeInfo")
    public DataResult getCargoTypeInfo(){
        try {
            List<TbCargoTypeDTO> cargoTypeList = tbCargoTypeService.getCargoTypeInfo();
            logger.info("查询货物类型成功");
            return DataResult.response(ResponseStatusEnum.SUCCESS).setData(cargoTypeList);
        }catch (Exception e){
            logger.error("错误 : {}" + e);
            return DataResult.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
