package com.suchao.learn.redis.cases.hash;

import com.suchao.learn.redis.util.ShortUrlUtil;
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
 * 社交网站（比如微博）的网址点击追踪机制 -- hash 机构
 * <p>
 * 存储短连接，并记录有多少人点击了这个短连接地址
 *
 * @author suchao
 * @date 2021/3/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_046_ShortUrlTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String SHORT_URL_ACCESS_COUNT_KEY = "short_url_access_count";

    public static final String SHORT_URL_SEED_KEY = "short_url_seed";

    public static final String SHORT_URL_MAPPING_KEY = "short_url_mapping";

    @Before
    public void setUp() {
        stringRedisTemplate.delete(SHORT_URL_ACCESS_COUNT_KEY);
        stringRedisTemplate.delete(SHORT_URL_SEED_KEY);
        stringRedisTemplate.delete(SHORT_URL_MAPPING_KEY);

        // 设置一个初始 seed
        stringRedisTemplate.opsForValue().set(SHORT_URL_SEED_KEY, "10000019");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void useAsShortUrl() {
        Long seed = stringRedisTemplate.opsForValue().increment(SHORT_URL_SEED_KEY);
        String shortUrl = ShortUrlUtil.getShortUrl(seed.intValue());
        log.info("短网址是: {}", shortUrl);
        storeShortUrlAndUrlMappings("http://www.baidu.com", shortUrl);

        // 模拟进行点击了短网址
        for (int i = 0; i < 125; i++) {
            incrementShortUrlAccess(shortUrl);
        }
        Assert.assertEquals("125", stringRedisTemplate.opsForHash().get(SHORT_URL_ACCESS_COUNT_KEY, shortUrl));
    }

    public void incrementShortUrlAccess(String shortUrl) {
        stringRedisTemplate.opsForHash().increment(SHORT_URL_ACCESS_COUNT_KEY, shortUrl, 1);
    }

    public void storeShortUrlAndUrlMappings(String url, String shortUrl) {
        stringRedisTemplate.opsForHash().put(SHORT_URL_MAPPING_KEY, shortUrl, url);
    }
}
