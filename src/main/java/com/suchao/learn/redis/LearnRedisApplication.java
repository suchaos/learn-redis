package com.suchao.learn.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class LearnRedisApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LearnRedisApplication.class, args);
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Override
    public void run(String... args) throws Exception {
        //stringRedisTemplate.opsForValue().set("aaa", "bbb");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
    }
}
