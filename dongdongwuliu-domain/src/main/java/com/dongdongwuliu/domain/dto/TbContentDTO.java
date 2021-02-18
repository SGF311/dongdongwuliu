package com.dongdongwuliu.domain.dto;


import lombok.Data;

@Data
public class TbContentDTO {

    private Long id;

    private Long categoryId;

    private String title;

    private String url;

    private String pic;

    private String status;

    private Integer sortOrder;


}