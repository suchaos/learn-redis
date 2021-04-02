package com.suchao.learn.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO description class
 *
 * @author suchao
 * @date 2021/2/24
 */
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/get/redis/{name}")
    public String getRedisValue(@PathVariable String name) {
        return stringRedisTemplate.opsForValue().get(name);
    }

    @GetMapping("/set/redis/{name}/{value}")
    public String getRedisValue(@PathVariable String name, @PathVariable String value) {
        stringRedisTemplate.opsForValue().set(name, value);
        return "success";
    }
}
