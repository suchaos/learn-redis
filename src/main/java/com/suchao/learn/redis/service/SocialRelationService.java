package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 社交关系 -- redis set
 *
 * @author suchao
 * @date 2021/3/31
 */
@Service
public class SocialRelationService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 关注别人
     *
     * @param userId       userId
     * @param followUserId followUserId
     */
    public void follow(long userId, long followUserId) {
        // 自己关注的人
        stringRedisTemplate.opsForSet().add(getFollowUsersKeyByUserId(userId), String.valueOf(followUserId));
        // 关注自己的人
        stringRedisTemplate.opsForSet().add(getFollowsKeyByUserId(followUserId), String.valueOf(userId));
    }

    /**
     * 取消关注别人
     *
     * @param userId       userId
     * @param followUserId followUserId
     */
    public void unfollow(long userId, long followUserId) {
        // 自己关注的人
        stringRedisTemplate.opsForSet().remove(getFollowUsersKeyByUserId(userId), String.valueOf(followUserId));
        // 关注自己的人
        stringRedisTemplate.opsForSet().remove(getFollowsKeyByUserId(followUserId), String.valueOf(userId));
    }

    /**
     * 查看关注自己的人
     *
     * @param userId userId
     * @return Set<String>
     */
    public Set<String> getFollowers(long userId) {
        return stringRedisTemplate.opsForSet().members(getFollowsKeyByUserId(userId));
    }

    /**
     * 查看关注自己的人的数
     *
     * @param userId userId
     * @return
     */
    public long getFollowersCount(long userId) {
        Long size = stringRedisTemplate.opsForSet().size(getFollowsKeyByUserId(userId));
        return size != null ? size : 0;
    }

    /**
     * 查看自己关注的人
     *
     * @param userId userId
     * @return Set<String>
     */
    public Set<String> getFollowUsers(long userId) {
        return stringRedisTemplate.opsForSet().members(getFollowUsersKeyByUserId(userId));
    }

    /**
     * 查看自己关注的人的数
     *
     * @param userId userId
     * @return
     */
    public long getFollowUsersCount(long userId) {
        Long size = stringRedisTemplate.opsForSet().size(getFollowUsersKeyByUserId(userId));
        return size != null ? size : 0;
    }

    /**
     * 获取两个用户共同关注的人 -- sinter
     *
     * @param userId      userId
     * @param otherUserId otherUserId
     * @return Set<String>
     */
    public Set<String> getSameFollowUsers(long userId, long otherUserId) {
        return stringRedisTemplate.opsForSet().
                intersect(getFollowUsersKeyByUserId(userId), getFollowUsersKeyByUserId(userId));
    }

    /**
     * 获取给我推荐的可关注人 -- 就是我关注的某个好友所关注的人(我此时没有关注的) -- sdiff
     *
     * @param userId      userId
     * @param otherUserId otherUserId
     * @return Set<String>
     */
    public Set<String> getRecommendFollowUsers(long userId, long otherUserId) {
        // 注意：diff 的两个 key 的顺序不要反了
        return stringRedisTemplate.opsForSet().
                difference(getFollowUsersKeyByUserId(otherUserId), getFollowUsersKeyByUserId(userId));
    }

    /**
     * 关注自己的人 -- followers
     *
     * @param userId userId
     * @return key
     */
    private String getFollowsKeyByUserId(long userId) {
        return "user::" + userId + "::followers";
    }

    /**
     * 自己关注的人 -- followUsers
     *
     * @param userId userId
     * @return key
     */
    private String getFollowUsersKeyByUserId(long userId) {
        return "user::" + userId + "::follow_users";
    }
}
