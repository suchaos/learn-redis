package com.suchao.learn.redis.cases.set;

import com.suchao.learn.redis.service.MomentsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

/**
 * 朋友圈点赞 -- redis set
 *
 * @author suchao
 * @date 2021/3/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_055_MomentLikeTest {

    @Autowired
    private MomentsService momentsService;

    @Test
    public void like() {
        long userId = 1;
        long momentId = 150;
        long friendId = 2;
        long otherFriendId = 3;

        // 两个朋友对 Id 为 1 的 momentId 为 150 的朋友圈点赞
        momentsService.likeMoment(friendId, momentId);
        momentsService.likeMoment(otherFriendId, momentId);

        Assert.assertEquals(2, momentsService.getMomentLikeUsersCount(momentId));
        Assert.assertArrayEquals(
                Arrays.asList("2", "3").toArray(new java.lang.String[0]),
                momentsService.getMomentLikeUsers(momentId).toArray(new java.lang.String[0]));

        Assert.assertTrue(momentsService.hasLikedMoment(friendId, momentId));
        Assert.assertTrue(momentsService.hasLikedMoment(otherFriendId, momentId));

        // 其中一个朋友取消点赞
        momentsService.dislikeMoment(friendId, momentId);
        Assert.assertFalse(momentsService.hasLikedMoment(friendId, momentId));
        Assert.assertEquals(1, momentsService.getMomentLikeUsersCount(momentId));
        Assert.assertArrayEquals(
                Collections.singletonList("3").toArray(new String[0]),
                momentsService.getMomentLikeUsers(momentId).toArray(new String[0]));
    }
}
