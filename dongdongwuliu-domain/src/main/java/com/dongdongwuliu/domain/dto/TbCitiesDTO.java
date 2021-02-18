package com.dongdongwuliu.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: 你哥
 * @Date: 2021/2/2 18:10
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbCitiesDTO implements Serializable {

    private Integer id;

    private String cityid;

    private String city;

    private String provinceid;

}