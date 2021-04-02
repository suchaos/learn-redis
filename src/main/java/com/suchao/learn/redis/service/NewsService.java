package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

/**
 * 新闻浏览 -- redis zset
 *
 * @author suchao
 * @date 2021/4/1
 */
@Service
public class NewsService {


    public static final String NEWS = "news";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 增加一篇新闻 -- 按照时间戳排序
     *
     * @param newsId newsId
     */
    public void addNews(long newsId) {
        addNews(newsId, LocalDateTime.now());
    }

    /**
     * 增加一篇新闻 -- 按照时间戳排序
     *
     * @param newsId
     * @param localDateTime
     */
    public void addNews(long newsId, LocalDateTime localDateTime) {
        stringRedisTemplate.opsForZSet().add(NEWS, String.valueOf(newsId),
                localDateTime.toEpochSecond(ZoneOffset.ofHours(8)));
    }

    /**
     * 搜索在这个时间段内的从 Index 开始的 count 条新闻
     *
     * @param maxTimeStamp
     * @param minTimeStamp
     * @param index
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> searchNews(long maxTimeStamp, long minTimeStamp, int index, int count) {
        return stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(NEWS, minTimeStamp, maxTimeStamp, index, count);
    }

    /**
     * 搜索在这个时间段内的从 Index 开始的 count 条新闻
     *
     * @param max
     * @param min
     * @param index
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> searchNews(LocalDateTime max, LocalDateTime min, int index, int count) {
        long maxTimeStamp = max.toEpochSecond(ZoneOffset.ofHours(8));
        long minTimeStamp = min.toEpochSecond(ZoneOffset.ofHours(8));
        return stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(NEWS, minTimeStamp, maxTimeStamp, index, count);
    }
}
