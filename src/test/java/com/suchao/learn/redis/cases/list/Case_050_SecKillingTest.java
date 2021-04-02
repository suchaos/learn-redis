package com.suchao.learn.redis.cases.list;

import com.suchao.learn.redis.service.SecKillingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 秒杀活动
 *
 * @author suchao
 * @date 2021/3/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_050_SecKillingTest {

    @Autowired
    private SecKillingService secKillingService;

    @Test
    public void seckilling() {
        for (int i = 0; i < 30; i++) {
            secKillingService.enqueueSecKillingRequest("第" + (i+1) + "个秒杀请求");
        }

        for (int i = 0; i < 30; i++) {
            String killingRequest = secKillingService.dequeueSecKillingRequest();
            log.info("killingRequest: " + killingRequest);
        }
    }
}
