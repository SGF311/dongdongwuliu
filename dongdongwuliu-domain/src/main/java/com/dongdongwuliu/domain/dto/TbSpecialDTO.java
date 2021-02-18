package com.dongdongwuliu.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TbSpecialDTO
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/3 17:17
 * @Version 1.0
 **/
@Data
public class TbSpecialDTO implements Serializable {

//    @JsonSerialize(using = ToStringSerializer.class)
    private Integer id;

    private String startProvince;

    private String endProvince;
}

 

