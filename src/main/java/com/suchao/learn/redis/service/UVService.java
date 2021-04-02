package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * UV 统计 -- redis set
 *
 * @author suchao
 * @date 2021/3/30
 */
@Service
public class UVService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 增加一次用户访问记录
     *
     * @param userId 用户 ID
     */
    public void addUserAccess(long userId) {
        // 每天一个 set
        stringRedisTemplate.opsForSet()
                .add(getUserAccessSetKey(), String.valueOf(userId));
    }

    /**
     * 获取当天的网站 UV -- redis scard
     */
    public long getUV() {
        return stringRedisTemplate.opsForSet().size(getUserAccessSetKey());
    }

    private String getUserAccessSetKey() {
        return "user_access_set_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
    }
}
