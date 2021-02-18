package com.dongdongwuliu.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: 你哥
 * @Date: 2021/2/2 18:32
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbAreasVO  implements Serializable {

    private Integer id;

    private String areaid;

    private String area;

    private String cityid;

}
