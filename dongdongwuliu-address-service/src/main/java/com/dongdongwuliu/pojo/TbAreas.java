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
@TableName("tb_areas")
public class TbAreas implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER)
    private Integer id;

    private String areaid;

    private String area;

    private String cityid;

}