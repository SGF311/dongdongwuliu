package com.dongdongwuliu.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: 你哥
 * @Date: 2021/2/3 21:21
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbCourierDTO implements Serializable {

    private Long id;

    private Long orderId;

    private Double distance;

    private Integer status;

    private Integer isDelete;

    private Integer personId;
}
