package com.dongdongwuliu.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddressDTO implements Serializable {
    private Long id;

    private String userId;

    private String provinceId;

    private String cityId;

    private String townId;

    private String mobile;

    private String address;

    private String contact;

    private String isDefault;

    private String notes;

    private Date createDate;

    private String alias;
}