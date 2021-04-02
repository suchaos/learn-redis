package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * HyperLogLog 来进行统计 UV
 *
 * @author suchao
 * @date 2021/4/2
 */
@Service
public class UVByHyperLogLogService {

    public static final String DATE_PATTERN = "yyyy_MM_dd";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 增加 UV
     *
     * @param userId userId
     */
    public void addUV(long userId) {
        stringRedisTemplate.opsForHyperLogLog().add(getUVTodayKey(), String.valueOf(userId));
    }

    /**
     * 获取日活
     *
     * @return
     */
    public long getDayUV() {
        return stringRedisTemplate.opsForHyperLogLog().size(getUVTodayKey());
    }

    public long getWeekUV() {
        String[] uvWeekKeys = getUVWeekKeys();
        return stringRedisTemplate.opsForHyperLogLog().union(uvWeekKeys[0] + uvWeekKeys[uvWeekKeys.length - 1], uvWeekKeys);
    }


    private String getUVTodayKey() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        return "hyperloglog_uv_" + today;
    }

    private String[] getUVWeekKeys() {
        LocalDate today = LocalDate.now();

        String[] keys = new String[7];
        for (int i = 0; i < keys.length; i++) {
            String day = today.minusDays(i).format(DateTimeFormatter.ofPattern(DATE_PATTERN));
            keys[i] = "hyperloglog_uv_" + day;
        }
        return keys;
    }
}
