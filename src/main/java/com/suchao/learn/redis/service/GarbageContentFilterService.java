package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 垃圾内容过滤 -- hyperloglog
 *
 * @author suchao
 * @date 2021/4/2
 */
@Service
public class GarbageContentFilterService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 判断当前内容是否是垃圾内容
     *
     * @param content content
     * @return
     */
    public boolean isGarbageContent(String content) {
        return stringRedisTemplate.opsForHyperLogLog().add("hyperloglog_garbage_content", content) == 0;
    }
}
