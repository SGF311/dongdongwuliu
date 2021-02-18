package com.dongdongwuliu.domain.vo;

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
public class TbOrderVO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    private BigDecimal estimatedPrice;

    private BigDecimal amountPayable;

    private BigDecimal payment;

    private String paymentType;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Integer cargoId;

    private String cargoName;

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

    private String siteName;

    private Long outTradeNo;

    private BigDecimal score;

    private Integer isScore;

    private Integer isDelete;

    private Integer type;

    private String longitude;

    private String latitude;

    private Long circuitId;

    //---------------

    //详细信息

    private BigDecimal deliveryCost;

    private BigDecimal trafficExpense;

    private BigDecimal marginValue;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date paymentTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date consignTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date closeTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitTime;

    private BigDecimal cargoWeight;

    private BigDecimal cargoVolume;

    private String invoiceType;

    private String sourceType;

    private double distance; //配送员距离

    private Integer personId;//配送员id

    //用户名
    private String username;

}