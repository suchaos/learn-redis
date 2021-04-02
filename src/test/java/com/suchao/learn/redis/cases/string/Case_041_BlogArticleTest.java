package com.suchao.learn.redis.cases.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 博客的发布
 * 使用 Redis mget mset 批量设置
 *
 * @author suchao
 * @date 2021/3/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_041_BlogArticleTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    static Collection<String> articlesKeys;

    static Collection<String> articlesValues;

    static LocalDateTime now = LocalDateTime.now();

    static {
        articlesKeys = new ArrayList<>(4);
        articlesKeys.add("article:1:title");
        articlesKeys.add("article:1:content");
        articlesKeys.add("article:1:author");
        articlesKeys.add("article:1:time");

        articlesValues = new ArrayList<>(4);
        articlesValues.add("学习 redis");
        articlesValues.add("具体的学习内容包括：XXXXX");
        articlesValues.add("suchao");
        articlesValues.add(now.toString());
    }

    @Before
    public void setUp() {
        stringRedisTemplate.delete(articlesKeys);
    }

    @Test
    public void useAsBLogArticlePuslish() {
        // 博客的发布 MSETNX
        Map<String, String> articlesPairs = new HashMap<>();
        articlesPairs.put("article:1:title", "学习 redis");
        articlesPairs.put("article:1:content", "具体的学习内容包括：XXXXX");
        articlesPairs.put("article:1:author", "suchao");
        articlesPairs.put("article:1:time", now.toString());
        stringRedisTemplate.opsForValue().multiSetIfAbsent(articlesPairs);

        // mgetnx
        List<String> articlesFromRedis = stringRedisTemplate.opsForValue().multiGet(articlesKeys);
        Assert.assertNotNull(articlesFromRedis);
        articlesFromRedis.forEach(log::info);
        Assert.assertArrayEquals(articlesValues.toArray(new String[0]),
                articlesFromRedis.toArray(new String[0]));

        // 博客的修改
        articlesPairs.put("article:1:title", "修改后的学习 redis");
        articlesPairs.put("article:1:content", "修改后的具体的学习内容包括：XXXXX");
        articlesPairs.put("article:1:author", "修改后的suchao");
        articlesPairs.put("article:1:time", now.toString());
        stringRedisTemplate.opsForValue().multiSet(articlesPairs);

        // 统计字数 strlen 与 预览 getrange
        Long contentSize = stringRedisTemplate.opsForValue().size("article:1:content");
        Assert.assertNotNull(contentSize);
        log.info("content 长度统计: {}", contentSize.intValue());

        String contentPreview = stringRedisTemplate.opsForValue().get("article:1:content", 0, 5);
        log.info("content 预览: {}", contentPreview);
    }
}
