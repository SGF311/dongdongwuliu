package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_site")
public class TbSite implements Serializable {
    @TableId(value = "site_id",type = IdType.AUTO)
    private Integer siteId;

    private String siteName;

    private String pid;

    private String cid;

    private String aid;

    private String address;

    private String phone;

    //联查用字段  省市县名称
    @TableField(exist = false)
    private String province;

    @TableField(exist = false)
    private String city;

    @TableField(exist = false)
    private String area;

}