package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 音乐排行榜 -- redis zset
 *
 * @author suchao
 * @date 2021/4/1
 */
@Service
public class SongRankingListService {

    public static final String SONG_RANKING_LIST = "song_ranking_list";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 将新的音乐加入排行榜中
     *
     * @param songId songId
     */
    public void addSong(long songId) {
        stringRedisTemplate.opsForZSet().add(SONG_RANKING_LIST, String.valueOf(songId), 0);
    }

    /**
     * 增加或减少歌曲分数
     *
     * @param songId songId
     * @param score  score
     */
    public void incrementSongScore(long songId, double score) {
        stringRedisTemplate.opsForZSet().incrementScore(SONG_RANKING_LIST, String.valueOf(songId), score);
    }

    /**
     * 获取歌曲排名
     *
     * @param songId songId
     * @return 排名
     */
    public long getSongRank(long songId) {
        return stringRedisTemplate.opsForZSet().reverseRank(SONG_RANKING_LIST, String.valueOf(songId));
    }

    /**
     * 获取音乐排行榜
     *
     * @return Set<String>
     */
    public Set<ZSetOperations.TypedTuple<String>> getSongRankingList() {
        return stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(SONG_RANKING_LIST, 0, 100);
    }

    /**
     * 获取排行榜的前几位
     *
     * @param topN 前几位
     * @return Set<String>
     */
    public Set<ZSetOperations.TypedTuple<String>> getSongRankingList(int topN) {
        return stringRedisTemplate.opsForZSet().reverseRangeWithScores(SONG_RANKING_LIST, 0, topN);
    }

    public static void main(String[] args) {

    }
}
