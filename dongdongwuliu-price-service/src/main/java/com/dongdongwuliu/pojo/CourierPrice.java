package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("courier_price")
public class CourierPrice implements Serializable {
    @TableId(value = "id",type = IdType.ID_WORKER)
    private Long id;

    private Integer siteId;

    private Integer siteScope;

    private Integer weightPrice;

    private Integer volumePrice;

    private String remark;

}