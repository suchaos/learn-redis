package com.suchao.learn.redis.cases.zset;

import com.suchao.learn.redis.service.SongRankingListService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * 音乐排行榜
 *
 * @author suchao
 * @date 2021/4/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_061_SongRankingListTest {

    @Autowired
    private SongRankingListService songRankingListService;

    @Test
    public void songRankingList() {
        for (int i = 0; i < 20; i++) {
            songRankingListService.addSong(i + 1);
        }

        songRankingListService.incrementSongScore(5, 3.2);
        songRankingListService.incrementSongScore(7, 5.6);
        songRankingListService.incrementSongScore(15, 9.6);

        long songRank = songRankingListService.getSongRank(5);
        log.info("歌曲id为 5 的排名为: {}", songRank + 1);

        Set<ZSetOperations.TypedTuple<String>> songRankingList = songRankingListService.getSongRankingList(3);
        songRankingList.forEach(tuple -> {
            log.info("score is {}, and value is {}", tuple.getScore(), tuple.getValue());
        });
    }
}
