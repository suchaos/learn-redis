package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品搜索案例 -- 反向索引 -- redis set
 *
 * @author suchao
 * @date 2021/4/1
 */
@Service
public class ProductSearchService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加商品的时候附带一些关键词
     *
     * @param productId 商品ID
     * @param keywords  商品关键词
     */
    public void addProduct(long productId, List<String> keywords) {
        keywords.forEach(keyword -> {
            stringRedisTemplate.opsForSet().add(getKeyByKeyword(keyword), String.valueOf(productId));
        });
    }

    /**
     * 根据多个关键词搜索商品
     *
     * @param keywords 关键词列表
     * @return 商品集合 / null
     *
     *
     *
     */
    public Set<String> searchProduct(List<String> keywords) {
        if (keywords == null || keywords.size() <= 0) {
            return null;
        }
        List<String> keys = keywords.stream().map(this::getKeyByKeyword).collect(Collectors.toList());
        if (keys.size() == 1) {
            return stringRedisTemplate.opsForSet().members(keys.get(0));
        }
        return stringRedisTemplate.opsForSet().intersect(keys.get(0), keys);
    }

    private String getKeyByKeyword(String keyword) {
        return "keyword::" + keyword + "::products";
    }
}
