package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
@Data
public class TbRole implements Serializable {
    @TableId(value = "rid",type = IdType.AUTO)
    private Integer rid;

    private String rname;


}