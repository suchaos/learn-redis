package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 朋友圈点赞案例 -- redis set
 * <p>
 * sadd, srem, scard, smembers, sismember
 *
 * @author suchao
 * @date 2021/3/31
 */
@Service
public class MomentsService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 对朋友圈进行点赞  -- sadd
     *
     * @param userId   点赞用户ID
     * @param momentId 点赞的朋友圈ID
     */
    public void likeMoment(long userId, long momentId) {
        stringRedisTemplate.opsForSet().add(getKeyByMomentId(momentId), String.valueOf(userId));
    }

    /**
     * 取消点赞 -- srem
     *
     * @param userId   userId
     * @param momentId momentId
     */
    public void dislikeMoment(long userId, long momentId) {
        stringRedisTemplate.opsForSet().remove(getKeyByMomentId(momentId), String.valueOf(userId));
    }

    /**
     * 是否对某条朋友圈点赞过 -- sismember
     *
     * @param userId   userId
     * @param momentId momentId
     * @return
     */
    public boolean hasLikedMoment(long userId, long momentId) {
        return stringRedisTemplate.opsForSet().isMember(getKeyByMomentId(momentId), String.valueOf(userId));
    }

    /**
     * 获取某条朋友圈的点赞的所有人 -- smembers
     *
     * @param momentId momentId
     * @return
     */
    public Set<String> getMomentLikeUsers(long momentId) {
        return stringRedisTemplate.opsForSet().members(getKeyByMomentId(momentId));
    }

    /**
     * 获取某条朋友圈的点赞人数
     *
     * @param momentId momentId
     * @return 点赞人数
     */
    public long getMomentLikeUsersCount(long momentId) {
        Long size = stringRedisTemplate.opsForSet().size(getKeyByMomentId(momentId));
        return size != null ? size : 0;
    }


    private String getKeyByMomentId(long momentId) {
        return "moment_like_users::" + momentId;
    }
}
