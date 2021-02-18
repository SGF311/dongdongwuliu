package com.dongdongwuliu.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourierPriceVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer siteId;

    private Integer siteScope;

    private Integer weightPrice;

    private Integer volumePrice;

    private String remark;

}