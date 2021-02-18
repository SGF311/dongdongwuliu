package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_person")
public class TbPerson implements Serializable {
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer uid;

    private String uname;

    private String upwd;

    private Integer type;

    private String longitude;

    private String latitude;

    private Long phone;

    private String salt;
    @TableField("sid")
    private Integer sid;


}