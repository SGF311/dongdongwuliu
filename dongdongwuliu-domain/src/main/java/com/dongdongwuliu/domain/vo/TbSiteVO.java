package com.dongdongwuliu.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TbSiteVO implements Serializable {
    private Integer siteId;

    private String siteName;

    private String pid;

    private String cid;

    private String aid;

    private String address;

    private String phone;
    //联查用字段  省市县名称
    private String province;

    private String city;

    private String area;

}