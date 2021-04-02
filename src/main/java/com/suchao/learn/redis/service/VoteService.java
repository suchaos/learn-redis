package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 投票统计 -- redis set
 * <p>
 * sadd, srem, scard, smembers, sismember
 *
 * @author suchao
 * @date 2021/3/31
 */
@Service
public class VoteService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户userid 对 某县投票  -- sadd
     *
     * @param userId     userId 投票人id
     * @param voteItemId voteItemId
     */
    public void vote(long userId, long voteItemId) {
        stringRedisTemplate.opsForSet().add(getKeyByVoteItemId(voteItemId), String.valueOf(userId));
    }

    /**
     * 是否对某项投票过 -- sismember
     *
     * @param userId     userId
     * @param voteItemId voteItemId
     * @return
     */
    public boolean hasVoted(long userId, long voteItemId) {
        Boolean result = stringRedisTemplate.opsForSet().isMember(getKeyByVoteItemId(voteItemId), String.valueOf(userId));
        return result != null ? result : false;
    }

    /**
     * 获取对某项投票过的所有人 -- smembers
     *
     * @param voteItemId voteItemId
     * @return
     */
    public Set<String> getVotedUsers(long voteItemId) {
        return stringRedisTemplate.opsForSet().members(getKeyByVoteItemId(voteItemId));
    }

    /**
     * 获取对某项投票过的人数
     *
     * @param voteItemId voteItemId
     * @return 点赞人数
     */
    public long getVotedUsersCount(long voteItemId) {
        Long size = stringRedisTemplate.opsForSet().size(getKeyByVoteItemId(voteItemId));
        return size != null ? size : 0;
    }


    private String getKeyByVoteItemId(long voteItemId) {
        return "vote_item::" + voteItemId;
    }
}
