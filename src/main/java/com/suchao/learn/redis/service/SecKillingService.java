package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 秒杀活动的公平队列 -- redis list 结构
 *
 * @author suchao
 * @date 2021/3/30
 */
@Service
public class SecKillingService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 秒杀请求入队
     *
     * @param secKillingRequest 秒杀请求
     */
    public void enqueueSecKillingRequest(String secKillingRequest) {
        stringRedisTemplate.opsForList().leftPush("sec_killing_request_queue", secKillingRequest);
    }

    /**
     * 秒杀请求出队
     * @return 秒杀请求
     */
    public String dequeueSecKillingRequest() {
        return stringRedisTemplate.opsForList().rightPop("sec_killing_request_queue");
    }
}
