package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//用户优惠券
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_discount_coupon")
@Accessors(chain = true)
public class TbUserDiscountCoupon implements Serializable {
    private Long id;

    //标题
    private String title;

    //商品图片
    private String smallPic;

    //优惠券面额
    private BigDecimal price;

    //添加日期
    private Date createTime;

    //优惠券开始时间
    private Date seckillStartTime;

    //优惠券结束时间
    private Date seckillEndTime;

    //'优惠券类型 0：无门槛 1：满减卷'
    private Integer type;

    //满多少
    private Integer sumPrice;

    //减多少
    private Integer losePrice;

    //描述
    private String introduction;

    //用户id
    @TableField("user_id")
    private Long userId;

    //删除状态  0： 未删除 （未使用） 1：已删除（已过期 ） 2：已使用
    private Integer isDelete;
}