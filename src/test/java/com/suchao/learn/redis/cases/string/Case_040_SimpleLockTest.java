package com.suchao.learn.redis.cases.string;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 使用 Redis 来作为最简单的分布式锁使用
 *
 * @author suchao
 * @date 2021/3/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Case_040_SimpleLockTest {

    public static final String LOCK = "lock_test";
    public static final String LOCK_Value = "lock_value";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Before
    public void setUp() {
        deleteLock(LOCK);
    }

    private Boolean deleteLock(String lockKey) {
        return stringRedisTemplate.delete(lockKey);
    }

    @Test
    public void useAsLock() {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(LOCK, LOCK_Value);
        System.out.println("加锁是否成功: " + result);
        Assert.assertTrue(result.booleanValue());

        result = stringRedisTemplate.opsForValue().setIfAbsent(LOCK, LOCK_Value);
        System.out.println("第二次加锁是否成功: " + result);
        Assert.assertFalse(result.booleanValue());

        result = deleteLock(LOCK);
        System.out.println("释放锁是否成功: " + result);
        Assert.assertTrue(result.booleanValue());

        result = stringRedisTemplate.opsForValue().setIfAbsent(LOCK, LOCK_Value);
        System.out.println("第三次加锁是否成功: " + result);
        Assert.assertTrue(result.booleanValue());
    }
}
