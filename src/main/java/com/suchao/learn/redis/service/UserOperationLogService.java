package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 用户网络操作日志案例
 *
 * @author suchao
 * @date 2021/4/2
 */
@Service
public class UserOperationLogService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 记录用户的操作日志 -- 是否进行了某种操作
     *
     * @param operation 某种操作
     * @param userId    用户Id
     */
    public void recordUserOperationLog(String operation, long userId) {
        stringRedisTemplate.opsForValue().setBit(getTodayRecordUserOperationKey(operation), userId, true);
    }

    /**
     * 判断用户今天是否执行了某个操作
     *
     * @param operation 某种操作
     * @param userId    用户Id
     * @return
     */
    public boolean hasOperated(String operation, long userId) {
        return stringRedisTemplate.opsForValue().getBit(getTodayRecordUserOperationKey(operation), userId);
    }

    /**
     * 获取 redis bitmap 的 key
     *
     * @param operation
     * @return
     */
    private String getTodayRecordUserOperationKey(String operation) {
        return "operation::" + operation + "::" + LocalDate.now() + "::log";
    }
}
