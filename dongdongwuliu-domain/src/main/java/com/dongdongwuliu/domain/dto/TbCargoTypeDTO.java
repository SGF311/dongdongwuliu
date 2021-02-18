package com.dongdongwuliu.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: 你哥
 * @Date: 2021/2/3 09:23
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbCargoTypeDTO implements Serializable {

    private Integer cargoId;

    private String cargoName;

}
