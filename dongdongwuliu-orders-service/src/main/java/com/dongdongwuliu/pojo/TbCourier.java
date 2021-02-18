package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_courier")
public class TbCourier implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER)
    private Long id;

    private Long orderId;

    private Double distance;

    private Integer status;

    private Integer isDelete;

    private Integer personId;

}