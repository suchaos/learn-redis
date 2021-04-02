package com.suchao.learn.redis.cases.bitmap;

import com.suchao.learn.redis.service.UserOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 用户网络操作日志案例
 *
 * @author suchao
 * @date 2021/4/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_068_UserOperationLogTest {

    @Autowired
    private UserOperationLogService userOperationLogService;

    @Test
    public void userOperationLog() {
        long userId = 100;
        String operation01 = "登录";
        String operation02 = "下载";

        userOperationLogService.recordUserOperationLog(operation01, userId);

        Assert.assertTrue(userOperationLogService.hasOperated(operation01, userId));
        Assert.assertFalse(userOperationLogService.hasOperated(operation02, userId));
    }
}
