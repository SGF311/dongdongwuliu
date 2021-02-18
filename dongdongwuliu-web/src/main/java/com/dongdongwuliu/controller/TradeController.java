package com.dongdongwuliu.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.dongdongwuliu.config.AlipayConfig;
import com.dongdongwuliu.feign.EsServiceFeign;
import com.dongdongwuliu.feign.TbOrderServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Auther: 你哥
 * @Date: 2020/12/23 21:12
 * @Description:
 */
@Controller
@RequestMapping("trade")
public class TradeController {

    @Autowired
    private TbOrderServiceFeign tbOrderServiceFeign;

    @Autowired
    private EsServiceFeign esServiceFeign;

    /* WIDout_trade_no :  //商户订单号，商户网站订单系统中唯一订单号，必填
        WIDtotal_amount : //付款金额，必填
        WIDsubject : //订单名称，必填
        WIDbody : //商品描述，可空
     */
    @RequestMapping("tradePagePay")
    @ResponseBody
    public String tradePagePay(String WIDout_trade_no,String WIDtotal_amount,String WIDsubject,String WIDbody){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDout_trade_no +"\","
                + "\"total_amount\":\""+ WIDtotal_amount +"\","
                + "\"subject\":\""+ WIDsubject +"\","
                + "\"body\":\""+ WIDbody +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    //异步
    /*
     * @Author 你哥
     * @Description //TODO
     * @Date 2020/12/23 21:43
     * @Param
     * out_trade_no : //商户订单号
     * trade_no : //支付宝交易号
     * trade_status : //交易状态
     * @return
     **/
    @RequestMapping("notifyUrl")
    @ResponseBody
    public String notifyUrl(HttpServletRequest request,String out_trade_no,String trade_no,String trade_status) throws AlipayApiException, UnsupportedEncodingException {
        /* *
         * 功能：支付宝服务器异步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

         *************************页面功能说明*************************
         * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
         * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
         * 如果没有收到该页面返回的 success
         * 建议该页面只做支付成功的业务逻辑处理，退款的处理请以调用退款查询接口的结果为准。
         */

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //验证签名是否被篡改
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

        /* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。
        */

        String parameter = request.getParameter("");

        if(signVerified) {//验证成功
            if(trade_status.equals("TRADE_FINISHED")){//交易成功
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){//付款成功
                System.out.println("付款成功");
                //付款成功时要修改状态为已支付待发货  同时修改es的状态
                //		    然后在添加进MongoDB中
                String username = request.getParameter("WIDsubject");
                String orderId = request.getParameter("WIDout_trade_no");
                System.out.println("用户名-》》》》》  " + username + ",,订单号---。》  " + orderId);
                //修改数据库订单状态
                tbOrderServiceFeign.updateStatus(username,orderId);
                //修改es中的订单状态
                esServiceFeign.updateStatusByUsernameByOrderId(username,orderId);
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }
            return "success";
        }else {//验证失败
            System.out.println("交易失败");
            return "fail";
        }
    }

    //同步
    /*
     * @Author 你哥
     * @Description //TODO
     * @Date 2020/12/23 21:48
     * @Param
     * out_trade_no : 商户订单号
     * trade_no : 支付宝交易号
     * total_amount : 付款金额
     * @return
     **/
    @RequestMapping("returnUrl")
    public String returnUrl(HttpServletRequest request,String out_trade_no,String trade_no,String total_amount) throws UnsupportedEncodingException, AlipayApiException {
        /* *
         * 功能：支付宝服务器同步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
         */

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            return "paysuccess";
        }else {
            return "payfail";
        }
    }

    //交易查询
    /**
     * @Author 你哥
     * @Description //TODO
     * @Date 2020/12/23 21:56
     * @Param [request, WIDTQout_trade_no : 商户订单号, WIDTQtrade_no : 支付宝交易号]
     *
     * @return java.lang.String
     **/
    @RequestMapping("tradeQuery")
    @ResponseBody
    public String tradeQuery(HttpServletRequest request,String WIDTQout_trade_no,String WIDTQtrade_no) throws AlipayApiException {
    //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDTQout_trade_no +"\","+"\"trade_no\":\""+ WIDTQtrade_no +"\"}");

        //请求
        String result = alipayClient.execute(alipayRequest).getBody();

        //输出
        return result;
    }

    //退款
    /**
     * @Author 你哥
     * @Description //TODO
     * @Date 2020/12/23 22:07
     * @Param [WIDTRout_trade_no : 商户订单号, WIDTRtrade_no : 支付宝交易号, WIDTRrefund_amount : 需要退款的金额
     *         , WIDTRrefund_reason : 退款的原因, WIDTRout_request_no : 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传]
     * @return java.lang.String
     **/
    @RequestMapping("tradeRefund")
    @ResponseBody
    public String tradeRefund(String WIDTRout_trade_no,String WIDTRtrade_no,String WIDTRrefund_amount
            ,String WIDTRrefund_reason,String WIDTRout_request_no) throws AlipayApiException {

//        WIDTRout_trade_no = "2020122422590902";
//        WIDTRtrade_no = "2020122422001419780501426538";
//        WIDTRrefund_reason = "不想要了";
//        WIDTRrefund_amount = "66";
//        WIDTRout_request_no = "111";
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDTRout_trade_no +"\","
                + "\"trade_no\":\""+ WIDTRtrade_no +"\","
                + "\"refund_amount\":\""+ WIDTRrefund_amount +"\","
                + "\"refund_reason\":\""+ WIDTRrefund_reason +"\","
                + "\"out_request_no\":\""+ WIDTRout_request_no +"\"}");

        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //输出
        return result;
    }

    //退款查询
    /**
     * @Author 你哥
     * @Description //TODO
     * @Date 2020/12/23 22:13
     * @Param [WIDRQout_trade_no : 商户订单号, WIDRQtrade_no : 支付宝交易号, WIDRQout_request_no : 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填]
     * @return java.lang.String
     **/
    @RequestMapping("tradeFastpayRefundQuery")
    @ResponseBody
    public String tradeFastpayRefundQuery(String WIDRQout_trade_no,String WIDRQtrade_no,String WIDRQout_request_no) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDRQout_trade_no +"\","
                +"\"trade_no\":\""+ WIDRQtrade_no +"\","
                +"\"out_request_no\":\""+ WIDRQout_request_no +"\"}");

        //请求
        String result = alipayClient.execute(alipayRequest).getBody();

        //输出
        return result;
    }



}
