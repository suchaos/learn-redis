package com.suchao.learn.redis.cases.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

/**
 * 审计日志
 * <p>
 * key 是日期, value 是每天的日志
 * <p>
 * 使用 append api
 *
 * @author suchao
 * @date 2021/3/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_043_LogTest {

    public static DateTimeFormatter logFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Before
    public void setUp() {
        stringRedisTemplate.delete(getTodayLogKey());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void useAsBLog() {
        String key = getTodayLogKey();
        log.info("key is : {}", key);

        // append api
        for (int i = 0; i < 10; i++) {
            stringRedisTemplate.opsForValue().append(key, "今天的第" + (i + 1) + "条操作日志\n");
        }

        String operationLog = stringRedisTemplate.opsForValue().get(key);
        log.info("log is: " + operationLog);
    }

    public String getTodayLogKey() {
        return "operation_log_" + LocalDate.now().format(logFormatter);
    }
}
