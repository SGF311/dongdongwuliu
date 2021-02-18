package com.dongdongwuliu.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CarriagePriceDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long priceId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long pathId;

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