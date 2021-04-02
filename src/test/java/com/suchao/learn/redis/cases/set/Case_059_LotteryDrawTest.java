package com.suchao.learn.redis.cases.set;

import com.suchao.learn.redis.service.LotteryDrawService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * 抽奖活动 -- redis set srandmember
 *
 * @author suchao
 * @date 2021/3/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_059_LotteryDrawTest {

    @Autowired
    private LotteryDrawService lotteryDrawService;

    @Test
    public void lotteryDraw() {
        int eventId = 100;
        for (int i = 0; i < 20; i++) {
            lotteryDrawService.addLotteryDrawCandidate(eventId, i + 1);
        }

        Set<String> lotteryDrawUsers = lotteryDrawService.doLotteryDraw(eventId, 3);
        lotteryDrawUsers.forEach(log::info);
    }
}
