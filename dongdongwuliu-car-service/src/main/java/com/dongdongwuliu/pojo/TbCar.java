package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_car")
public class TbCar implements Serializable {
    @TableId(value = "car_id",type = IdType.AUTO)
    private Integer carId;

    private String carName;

    private String carNumber;
    @TableField(strategy= FieldStrategy.IGNORED)
    private Integer siteId;

    private Integer status;

    private Integer dead;

    private String standby;

}