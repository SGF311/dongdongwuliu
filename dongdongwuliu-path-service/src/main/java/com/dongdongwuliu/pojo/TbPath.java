package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_path")
@Accessors(chain = true)
public class TbPath implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER)
    private Long id;

    //起始位置
    private String startProvince;

    //结束位置
    private String endProvince;

    //站点id  已逗号分隔  例如：1,2,3,4,5',
    private String siteId;

    private Integer startPid;

    private Integer endPid;
}