package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_cities")
public class TbCities implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER)
    private Integer id;

    private String cityid;

    private String city;

    private String provinceid;

}