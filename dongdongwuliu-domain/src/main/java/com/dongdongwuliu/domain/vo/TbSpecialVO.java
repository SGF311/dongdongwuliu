package com.dongdongwuliu.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TbSpecialVO
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/3 17:20
 * @Version 1.0
 **/
@Data
public class TbSpecialVO implements Serializable {

//    @JsonSerialize(using = ToStringSerializer.class)
    private Integer id;

    private String startProvince;

    private String endProvince;
}

 

