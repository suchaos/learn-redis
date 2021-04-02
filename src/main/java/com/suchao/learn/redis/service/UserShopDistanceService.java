package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 用户与商家的距离计算案例
 *
 * @author suchao
 * @date 2021/4/2
 */
@Service
public class UserShopDistanceService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加一个地理位置
     * @param name
     * @param longitude
     * @param latitude
     */
    public void addLocation(String name, double longitude, double latitude) {
//        stringRedisTemplate.opsForGeo().add("location_data", )
        //stringRedisTemplate.expire()
    }

}
