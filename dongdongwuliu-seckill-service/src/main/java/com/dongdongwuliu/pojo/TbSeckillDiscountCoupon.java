package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//优惠券秒杀表
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_seckill_discount_coupon")
@Accessors(chain = true)
public class TbSeckillDiscountCoupon implements Serializable {

    //id
//    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    //标题
    private String title;

    //商品图片
    private String smallPic;

    //优惠券面额
    private BigDecimal price;

    //添加日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    //秒杀商品数
    private Integer num;

    //剩余库存数
    private Integer stockCount;

    //优惠券类型 0：无门槛 1：满减卷
    private Integer type;

    //优惠券开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date seckillStartTime;

    //优惠券结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date seckillEndTime;

    //满多少
    private Integer sumPrice;

    //减多少
    private Integer losePrice;

    //描述
    private String introduction;
}