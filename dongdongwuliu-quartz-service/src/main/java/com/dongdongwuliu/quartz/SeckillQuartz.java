package com.dongdongwuliu.quartz;

import com.dongdongwuliu.feign.SeckillServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * @ClassName SeckillQuartz
 * @Deacription TODO
 * @Author gao jie
 * @Date 2021/2/4 19:27
 * @Version 1.0
 **/
@Component
public class SeckillQuartz {

    @Autowired
    private SeckillServiceFeign seckillServiceFeign;

    @Scheduled(cron = "0 0 0/30 * * ?")
    public void selectList1(){
        seckillServiceFeign.selectList1();
    }
}

 

