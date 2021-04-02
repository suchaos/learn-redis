package com.suchao.learn.redis.cases.string;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 使用 Redis 来作为最简单的缓存使用
 *
 * @author suchao
 * @date 2021/3/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Case_039_SimpleCacheTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void useAsCache() {

    }

}
