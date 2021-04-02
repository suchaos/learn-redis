package com.suchao.learn.redis.cases.zset;

import com.suchao.learn.redis.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

/**
 * 新闻浏览
 *
 * @author suchao
 * @date 2021/4/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_062_NewsTest {

    @Autowired
    private NewsService newsService;

    @Test
    public void news() {
        newsService.addNews(1, LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        newsService.addNews(2, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 10)));
        newsService.addNews(3, LocalDateTime.of(LocalDate.now(), LocalTime.NOON));
        newsService.addNews(4, LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

        LocalDateTime min = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime max = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));
        Set<ZSetOperations.TypedTuple<String>> typedTuples =
                newsService.searchNews(max, min, 0, 3);
        typedTuples.forEach(tuple -> log.info("score is {}, value is {}", tuple.getScore(), tuple.getValue()));
    }
}
