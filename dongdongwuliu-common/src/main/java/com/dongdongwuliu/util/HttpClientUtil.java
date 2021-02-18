package com.dongdongwuliu.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClientUtil {
    private static CloseableHttpClient httpclient;
    private static final String charset = "UTF-8";
    private static URI uri;
    static {
        //        1.创建Httpclient对象,相当于打开了浏览器
        httpclient = httpclient = HttpClients.createDefault();
    }

    /*doget无参数*/
    public static  String  doGet(String url){
        if (StringUtils.isBlank(url)){
            return null;
        }
//        2.创建http请求 get、post
        //创建doget请求,相当于浏览器输入地址
        HttpGet httpGet = new HttpGet(url);
//        3.执行请求，得到响应对象
        CloseableHttpResponse response= null;
        try {
            response = httpclient.execute(httpGet);
//        4.判断响应状态码是不是200，获取响应数据实体对象,200表示响应成功,一般用于get和post请求
            if (response.getStatusLine().getStatusCode() == 200){
//        5.获取响应数据
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, charset);
                System.out.println("响应结果为---->"+content);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//        6.关闭响应对象
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /*doget有参数*/
    public static  String  doGetParam(String url,String paramName,String param){
        if (StringUtils.isBlank(url)){
            return null;
        }
        URI uri = null;
//        2.创建http请求 get、post
        //创建doget请求,相当于浏览器输入地址
        try {
            uri = new URIBuilder(url).setParameter(paramName, param).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);
//        3.执行请求，得到响应对象
        CloseableHttpResponse response= null;
        try {
            response = httpclient.execute(httpGet);
//        4.判断响应状态码是不是200，获取响应数据实体对象,200表示响应成功,一般用于get和post请求
            if (response.getStatusLine().getStatusCode() == 200){
//        5.获取响应数据
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, charset);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//        6.关闭响应对象
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }




    /*doget有多参数*/
    public static  String  doGetParams(String url,Map<String,String> map){
        if (StringUtils.isBlank(url)){
            return null;
        }
        URI uri = null;
//        2.创建http请求 get、post
        //创建doget请求,相当于浏览器输入地址
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(),entry.getValue());
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);
//        3.执行请求，得到响应对象
        CloseableHttpResponse response= null;
        try {
            response = httpclient.execute(httpGet);
//        4.判断响应状态码是不是200，获取响应数据实体对象,200表示响应成功,一般用于get和post请求
            if (response.getStatusLine().getStatusCode() == 200){
//        5.获取响应数据
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, charset);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//        6.关闭响应对象
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /*dopost无参数*/
    public static  String  doPost(String url){
        if (StringUtils.isBlank(url)){
            return null;
        }
//        2.创建http请求 get、post
        //创建doget请求,相当于浏览器输入地址
        HttpPost httpPost = new HttpPost(url);
//        3.执行请求，得到响应对象
        CloseableHttpResponse response= null;
        try {
            response = httpclient.execute(httpPost);
//        4.判断响应状态码是不是200，获取响应数据实体对象,200表示响应成功,一般用于get和post请求
            if (response.getStatusLine().getStatusCode() == 200){
//        5.获取响应数据
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, charset);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//        6.关闭响应对象
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /*doPost有参数*/
    public static  String  doPostParam(String url,String paramName,String param){
        if (StringUtils.isBlank(url)){
            return null;
        }
        URI uri = null;
//        2.创建http请求 get、post
        //创建doget请求,相当于浏览器输入地址
        try {
            uri = new URIBuilder(url).setParameter(paramName, param).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost(uri);
//        3.执行请求，得到响应对象
        CloseableHttpResponse response= null;
        try {
            response = httpclient.execute(httpPost);
//        4.判断响应状态码是不是200，获取响应数据实体对象,200表示响应成功,一般用于get和post请求
            if (response.getStatusLine().getStatusCode() == 200){
//        5.获取响应数据
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, charset);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//        6.关闭响应对象
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /*post传递多个参数*/
    public static  String doPostParams(String url, Map<String,String> map) throws UnsupportedEncodingException {
//        1.创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        /*创建URI对象,传递参数*/
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        2.创建http请求 get、post
        HttpPost httpPost = new HttpPost(uri);
        /*传递多个参数*/
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"utf-8");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
//        3.执行请求，得到响应对象
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
//        4.判断响应状态码是不是200，获取响应数据实体对象,200表示响应成功,一般用于get和post请求
            if (response.getStatusLine().getStatusCode() == 200) {
//        5.获取响应数据
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "utf-8");
                System.out.println("响应结果为---->" + content);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//        6.关闭响应对象
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
