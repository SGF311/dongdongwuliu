package com.dongdongwuliu.controller;


import com.alibaba.fastjson.JSONObject;

import com.dongdongwuliu.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.util.Map;

/**
 * @Deacription TODO
 * @Author Lenovo
 * @Date 2021/1/6 19:32
 * @Version 1.0
 **/
@Controller
@RequestMapping("weather")
public class WeatherController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("getWeather")
    @ResponseBody
    public Map Weather(HttpServletRequest request) {
        //获取ip
        //String ip = getIpAdrress(request);
        // 获取所在的市
        String city = getPublicIp();
        System.out.println("城市:"+city);
        //掉接口
//        String object = restTemplate.getForObject("http://wthrcdn.etouch.cn/weather_mini?city=" + city, String.class);
        String city1 = HttpClientUtil.doGetParam("http://wthrcdn.etouch.cn/weather_mini", "city", city);
        Map map = JSONObject.parseObject(city1, Map.class);
        return map;
    }

    public  String getPublicIp() {
//        String serve = "$_SERVER['REMOTE_ADDR']";

//        String ip = restTemplate.getForObject("http://whois.pconline.com.cn/ip.jsp?ip=$_SERVER['REMOTE_ADDR']", String.class);
        String ip = HttpClientUtil.doGetParam("http://whois.pconline.com.cn/ip.jsp", "ip","$_SERVER['REMOTE_ADDR']");
        ip = ip.replaceAll("\r|\n", "");
        String[] arr = ip.split("市");
        String city = arr[0];
        if(city.contains("省")){
            String[] arr2 = city.split("省");
            return arr2[1]+"市";
        }
        return city+"市";
    }

//    /**
//     * 获取Ip地址
//     * @param request
//     * @return
//     */
//    private  String getIpAdrress(HttpServletRequest request) {
//        String Xip = request.getHeader("X-Real-IP");
//        String XFor = request.getHeader("X-Forwarded-For");
//        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
////多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = XFor.indexOf(",");
//            if(index != -1){
//                return XFor.substring(0,index);
//            }else{
//                return XFor;
//            }
//        }
//        XFor = Xip;
//        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
//            return XFor;
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getRemoteAddr();
//        }
//        return XFor;
//    }
//    /**
//     * @Description 获取城市
//     * @Date 21:30 2021/1/6
//     * @Param [ip]
//     * @return java.lang.String
//     **/
//    public  String getCity(String  ip) throws Exception{
//// 创建 GeoLite2 数据库
//        URL url = WeatherController.class.getClassLoader().getResource("GeoLite2-City.mmdb");
//        File file;
//        if (url != null) {
//            file = new File(url.getFile());
//        } else {
//            return null;
//        }
//// 读取数据库内容
//        DatabaseReader reader = new DatabaseReader.Builder(file).build();
//
//        InetAddress ipAddress = InetAddress.getByName("36.112.125.94");
//
//// 获取查询结果
//        CityResponse response = reader.city(ipAddress);
//
//
//
//// 获取城市
//        City city = response.getCity();
//        String cityName = city.getNames().get("zh-CN");
//        return cityName;
//    }
}
