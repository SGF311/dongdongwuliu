package com.dongdongwuliu.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TbPathVO
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/2 15:15
 * @Version 1.0
 **/
@Data
public class TbPathVO implements Serializable  {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //起始位置
    private String startProvince;

    //结束位置
    private String endProvince;

    //站点id  已逗号分隔  例如：1,2,3,4,5',
    private String siteId;

    private Integer startPid;

    private Integer endPid;


}

 

