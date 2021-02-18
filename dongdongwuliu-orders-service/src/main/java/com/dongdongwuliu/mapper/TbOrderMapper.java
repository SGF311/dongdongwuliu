package com.dongdongwuliu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.pojo.TbOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbOrderMapper extends BaseMapper<TbOrder> {

    //查询全部
    @Select("select o.*, od.*, ct.cargo_name cargoName, s.site_name siteName from tb_order o" +
            " left join tb_order_detail od on o.order_id = od.order_id" +
            " left join tb_cargo_type ct on ct.cargo_id = o.cargo_id" +
            " left join tb_site s on s.site_id = o.site_id")
    List<TbOrderVO> getInfo(@Param(Constants.WRAPPER) Wrapper<TbOrderVO> wrapper);


    //    语句中只有泛型是实体类其他都是固定写法
//    @Param(Constants.WRAPPER) Wrapper<TUser> wrapper;
//    ${ew.customSqlSegment}   MP会自动识别并作为where语句加入到自定义SQL语句中
//    @Select("select u.*,s.name sexName from t_user u left join t_sex s on u.sex = s.id ${ew.customSqlSegment}")
    @Select("select o.*, od.*, ct.cargo_name cargoName, s.site_name siteName from tb_order o" +
            " left join tb_order_detail od on o.order_id = od.order_id" +
            " left join tb_cargo_type ct on ct.cargo_id = o.cargo_id" +
            " left join tb_site s on s.site_id = o.site_id ${ew.customSqlSegment}")
    TbOrderVO findOrderByOrderId(@Param(Constants.WRAPPER) Wrapper<TbOrderVO> wrapper);

    List<TbOrderVO> findOrderByOutTradeNoBySenderMobileByStatus(@Param("outTradeNo") Long outTradeNo,@Param("senderMobile") String senderMobile,@Param("status") String status);
}