package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 自动补全 -- redis zset
 *
 * @author suchao
 * @date 2021/4/1
 */
@Service
public class AutoCompleteService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 搜索某个关键词 -- 相当于训练过程
     *
     * @param keyword keyword
     */
    public void search(String keyword) {
        char[] keywordCharArray = keyword.toCharArray();

        // 我喜欢学习

        // 我:: score + 我喜欢学习
        // 我喜:: score + 我喜欢学习
        StringBuilder potentialKeyword = new StringBuilder("");
        for (char keywordChar : keywordCharArray) {
            potentialKeyword.append(keywordChar);
            stringRedisTemplate.opsForZSet().
                    incrementScore(getPotentialKeywordKeyByChar(potentialKeyword.toString()), keyword, 1);
        }
    }

    /**
     * 获取自动补全的集合 -- 推荐 count 个
     *
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    public Set<String> getAutoCompleteList(String keyword, int start, int end) {
        return stringRedisTemplate.opsForZSet().reverseRange(getPotentialKeywordKeyByChar(keyword), start, end);
    }

    private String getPotentialKeywordKeyByChar(String potentialKeyword) {
        return "potential_keyword::" + potentialKeyword + "::keywords";
    }

    public static void main(String[] args) {
        char[] chars = "我喜欢学习".toCharArray();
        for (char c : chars) {
            System.out.println(c);
        }
    }
}
