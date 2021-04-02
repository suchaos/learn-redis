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
import java.time.format.DateTimeFormatter;

/**
 * 唯一 ID 生成器 -- incr
 *
 * @author suchao
 * @date 2021/3/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_044_UniqueIDTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String ORDER_ID_KEY = "order_id_counter";


    @Before
    public void setUp() {
        stringRedisTemplate.delete(ORDER_ID_KEY);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void useAsBLog() {
        for (int i = 0; i < 10; i++) {
            Long orderId = stringRedisTemplate.opsForValue().increment(ORDER_ID_KEY);
            Assert.assertNotNull(orderId);
            Assert.assertEquals(i + 1, orderId.longValue());
        }
    }
}
