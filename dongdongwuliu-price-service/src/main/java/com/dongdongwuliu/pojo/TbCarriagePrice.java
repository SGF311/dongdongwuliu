package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("tb_carriage_price")
public class TbCarriagePrice implements Serializable {
    @TableId(value = "price_id",type = IdType.ID_WORKER)
    private Long priceId;

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