package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("menu")
public class Menu implements Serializable {
    @TableId(value = "mid",type = IdType.AUTO)
    private Integer mid;

    private String text;

    private String url;

    private Integer pid;

}