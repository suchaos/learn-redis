package com.suchao.learn.redis.cases.set;

import com.suchao.learn.redis.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 投票 --  redis set
 *
 * @author suchao
 * @date 2021/3/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_056_VoteTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void vote() {
        int userId = 1;
        int voteItemId = 100;

        voteService.vote(1, 100);
        Assert.assertTrue(voteService.hasVoted(userId, voteItemId));
        Assert.assertEquals(1, voteService.getVotedUsersCount(voteItemId));
    }
}
