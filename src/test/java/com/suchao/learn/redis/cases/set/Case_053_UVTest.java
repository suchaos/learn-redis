package com.suchao.learn.redis.cases.set;

import com.suchao.learn.redis.service.UVService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 邮件发送任务
 *
 * @author suchao
 * @date 2021/3/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_053_UVTest {

    @Autowired
    private UVService uvService;

    @Test
    public void uv() {
        for (int i = 0; i < 100; i++) {
            long userId = i + 1;
            for (int j = 0; j < 10; j++) {
                uvService.addUserAccess(userId);
            }
        }

        long uv = uvService.getUV();
        log.info("uv is {}", uv);
        Assert.assertEquals(100, uv);
    }
}
