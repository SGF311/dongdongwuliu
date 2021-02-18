package com.dongdongwuliu.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName TbSeckillDiscountCouponVO
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/1/30 14:16
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TbSeckillDiscountCouponVO implements Serializable {

    private Long id;

    //标题
    private String title;

    //商品图片
    private String smallPic;

    //优惠券面额
    private BigDecimal price;

    //添加日期
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    //开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    //结束时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    //秒杀商品数
    private Integer num;

    //剩余库存数
    private Integer stockCount;

    //优惠券类型 0：无门槛 1：满减卷
    private Integer type;

    //优惠券开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date seckillStartTime;

    //优惠券结束时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date seckillEndTime;

    //满多少
    private Integer sumPrice;

    //减多少
    private Integer losePrice;

    //描述
    private String introduction;
}

 

