package com.dongdongwuliu.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ConnectVO {

    private String id;

    private String orderId; // 订单ID

    private String siteId;  // 站点ID

    private String content; // 内容

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;      // 更新时间

    private String carNumber;   // 车牌号

    private String courierId;   // 配送人

    private String courierPhone;    // 配送员电话
}
