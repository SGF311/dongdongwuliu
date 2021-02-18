package com.dongdongwuliu.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TbCarDTO implements Serializable {
    private Integer carId;

    private String carName;

    private String carNumber;

    private Integer siteId;

    private Integer status;

    private Integer dead;

    private String standby;

}