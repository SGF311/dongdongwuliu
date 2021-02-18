package com.dongdongwuliu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order")
public class TbOrder implements Serializable {

    @TableId(value = "order_id",type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    private BigDecimal estimatedPrice;

    private BigDecimal amountPayable;

    private BigDecimal payment;

    private String paymentType;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer cargoId;

    private String senderDetailedAddress;

    private String senderAreaName;

    private String senderMobile;

    private String senderZipCode;

    private String sender;

    private String receiverDetailedAddress;

    private String receiverAreaName;

    private String receiverMobile;

    private String receiverZipCode;

    private String receiver;

    private String siteId;

    private Long outTradeNo;

    private BigDecimal score;

    private Integer isScore;

    private Integer isDelete;

    private Long circuitId;

    private Integer type;

    private String longitude;

    private String latitude;

    //用户名
    private String username;

}