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

/**
 * 博客点赞次数 -- incr
 *
 * @author suchao
 * @date 2021/3/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_045_BlogLikesNumberTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String BLOG_ID_KEY = "article:1:likes";


    @Before
    public void setUp() {
        stringRedisTemplate.delete(BLOG_ID_KEY);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void useAsBLog() {
        for (int i = 0; i < 10; i++) {
            // 点赞
            Long orderId = stringRedisTemplate.opsForValue().increment(BLOG_ID_KEY);
            Assert.assertNotNull(orderId);
            Assert.assertEquals(i + 1, orderId.longValue());
        }
        String likes = stringRedisTemplate.opsForValue().get(BLOG_ID_KEY);
        Assert.assertEquals("10", likes);

        // 取消点赞
        stringRedisTemplate.opsForValue().decrement(BLOG_ID_KEY);
        likes = stringRedisTemplate.opsForValue().get(BLOG_ID_KEY);
        Assert.assertEquals("9", likes);
    }
}
