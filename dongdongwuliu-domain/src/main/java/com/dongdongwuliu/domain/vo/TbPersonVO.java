package com.dongdongwuliu.domain.vo;

import lombok.Data;

@Data
public class TbPersonVO {
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