package com.dongdongwuliu.domain.dto;

import lombok.Data;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@Data
public class CourierPriceDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer siteId;

    private Integer siteScope;

    private Integer weightPrice;

    private Integer volumePrice;

    private String remark;

}