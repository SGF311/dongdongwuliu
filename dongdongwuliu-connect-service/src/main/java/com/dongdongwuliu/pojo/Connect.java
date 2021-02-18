package com.dongdongwuliu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection="connect")    // 对应mongoDB数据库中的集合
public class Connect {

    @Id //主键标识，该属性的值会自动对应mongodb的主键字段"_id"，如果该属性名就叫“id”,则该注解可以省略，否则必须写
    private String id;

    @Indexed            // 对订单id加索引
    @Field("orderId")  // 该属性对应mongodb的字段的名字，如果一致，则无需该注解
    private String orderId; // 订单ID

    private String siteId;  // 站点ID

    private String content; // 内容

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;      // 更新时间

    private String carNumber;   // 车牌号

    private String courierId;   // 配送人

    private String courierPhone;    // 配送员电话
}
