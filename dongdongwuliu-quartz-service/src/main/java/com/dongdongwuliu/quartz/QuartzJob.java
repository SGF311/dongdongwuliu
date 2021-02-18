package com.dongdongwuliu.quartz;

import com.dongdongwuliu.feign.TbOrderServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * @Auther: 你哥
 * @Date: 2021/2/3 21:17
 * @Description:
 */
@Component
public class QuartzJob {

    @Autowired
    private TbOrderServiceFeign tbOrderServiceFeign;

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void selectVisitTime() throws ParseException {
        tbOrderServiceFeign.selectVisitTime();
    }
}
