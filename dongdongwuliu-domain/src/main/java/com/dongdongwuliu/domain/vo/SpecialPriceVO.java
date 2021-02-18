package com.dongdongwuliu.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SpecialPriceVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long priceId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long specialId;

    private Integer firstKg;

    private BigDecimal firstKgPrice;

    private Integer otherKg;

    private BigDecimal otherKgPrice;

    private Integer firstStere;

    private BigDecimal firstSterePrice;

    private Integer otherStere;

    private BigDecimal otherSterePrice;

    private String remake;
}