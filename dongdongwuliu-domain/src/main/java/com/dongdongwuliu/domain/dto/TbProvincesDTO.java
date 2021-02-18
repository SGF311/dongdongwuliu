package com.dongdongwuliu.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: 你哥
 * @Date: 2021/2/2 17:07
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbProvincesDTO  implements Serializable {

    private Integer id;

    private String provinceid;

    private String province;

}
