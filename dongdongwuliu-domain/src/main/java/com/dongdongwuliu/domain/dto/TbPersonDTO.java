package com.dongdongwuliu.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbPersonDTO implements Serializable {
    private Integer uid;

    private String uname;

    private String upwd;

    private Integer type;

    private String longitude;

    private String latitude;

    private Long phone;

    private String salt;

    private Integer sid;


}