package com.dongdongwuliu.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.dongdongwuliu.data.DataResult;
import com.dongdongwuliu.data.ResponseStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName FileController
 * @Deacription TODO
 * @Author gao jie
 * @Date 2020/12/14 16:46
 * @Version 1.0
 **/
@Controller
@RequestMapping("file")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping("upload")
    @ResponseBody
    public DataResult upload(@RequestParam("myFile")MultipartFile myFile) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String name = "oss-cn-beijing.aliyuncs.com";
        String endpoint = "https://" + name;
//        String domain = "oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4FybpE1N4LZT68QGX5et";
        String accessKeySecret = "cSPlJHRCy9SK5C3ERycqGizl65Ogux";
        String bucketName = "gj2020";
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = myFile.getOriginalFilename();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。

        //        String content = "Hello OSS";

        String url = "http://" + bucketName + "." + name + "/" + objectName;
        //上传内到指定的存储空间(bucketName)  并保存为指定的文件名称(objectName)
        try {
            ossClient.putObject(bucketName, objectName, myFile.getInputStream());
        } catch (IOException e) {
            logger.error("系统错误", e);
        }

        //关闭OSSClient
        ossClient.shutdown();
        return DataResult.response(ResponseStatusEnum.SUCCESS).setData(url);
    }
}

 

