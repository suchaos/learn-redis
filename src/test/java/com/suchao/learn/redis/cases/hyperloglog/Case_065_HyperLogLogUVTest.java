package com.suchao.learn.redis.cases.hyperloglog;

import com.suchao.learn.redis.service.UVByHyperLogLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * hyperloglog 来进行统计 UV
 *
 * @author suchao
 * @date 2021/4/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_065_HyperLogLogUVTest {

    @Autowired
    private UVByHyperLogLogService uvByHyperLogLogService;

    @Test
    public void uv() {
        for (int i = 0; i < 100000; i++) {
            uvByHyperLogLogService.addUV(i + 1);
        }

        long uv = uvByHyperLogLogService.getDayUV();
        log.info("today uv is : {}", uv);
    }
}
