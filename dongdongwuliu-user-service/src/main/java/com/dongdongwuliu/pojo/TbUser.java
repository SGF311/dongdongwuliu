package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tb_user")
public class TbUser {
    @TableId(value = "id",type = IdType.ID_WORKER)
    private Long id;

    private String username;

    private String password;

    private String salt;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    private String nickName;

    private String name;

    private String status;

    private String sex;

    private Date birthday;

    private Date lastLoginTime;


}