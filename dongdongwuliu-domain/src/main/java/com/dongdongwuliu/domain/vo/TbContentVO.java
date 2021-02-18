package com.dongdongwuliu.domain.vo;


import lombok.Data;

@Data
public class TbContentVO {

    private Long id;

    private Long categoryId;

    private String title;

    private String url;

    private String pic;

    private String status;

    private Integer sortOrder;


}