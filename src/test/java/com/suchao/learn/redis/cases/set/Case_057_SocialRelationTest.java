package com.suchao.learn.redis.cases.set;

import com.suchao.learn.redis.service.SocialRelationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Set;

/**
 * 社交关系 -- redis set
 *
 * @author suchao
 * @date 2021/3/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_057_SocialRelationTest {

    @Autowired
    private SocialRelationService socialRelationService;

    @Test
    public void social() {


        long userId = 1;
        long friendId = 2;
        long superStar = 3;

        // 定义关注链条
        socialRelationService.follow(userId, friendId);
        socialRelationService.follow(userId, superStar);
        socialRelationService.follow(friendId, superStar);

        // 明星看自己被哪些人关注了
        Set<String> superStarFollowers = socialRelationService.getFollowers(superStar);
        log.info("明星看自己被哪些人关注了: {}", superStarFollowers);
        Assert.assertArrayEquals(new String[]{"1", "2"}, superStarFollowers.toArray(new String[0]));
        Assert.assertEquals(2, socialRelationService.getFollowersCount(superStar));

        // userId 为 1 的人看自己关注了哪些人
        Set<String> followUsers = socialRelationService.getFollowUsers(userId);
        log.info("userId 为 1 的人看自己关注了哪些人: {}", followUsers);
        Assert.assertArrayEquals(new String[]{"2", "3"}, followUsers.toArray(new String[0]));
        Assert.assertEquals(2, socialRelationService.getFollowUsersCount(userId));
    }
}
