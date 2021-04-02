package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 抽奖案例 -- redis set --  srandmember
 *
 * @author suchao
 * @date 2021/3/31
 */
@Service
public class LotteryDrawService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加抽奖候选人
     *
     * @param lotteryDrawEventId 某次抽奖活动
     * @param userId             userId
     */
    public void addLotteryDrawCandidate(long lotteryDrawEventId, long userId) {
        stringRedisTemplate.opsForSet().add(
                getKeyByLotteryDrawEventId(lotteryDrawEventId), String.valueOf(userId));
    }

    /**
     * 实际进行抽奖
     *
     * @param lotteryDrawEventId 某次抽奖活动
     * @param count              几个奖
     * @return Set<String>
     */
    public Set<String> doLotteryDraw(long lotteryDrawEventId, int count) {
        return stringRedisTemplate.opsForSet().
                distinctRandomMembers(getKeyByLotteryDrawEventId(lotteryDrawEventId), count);
    }

    private String getKeyByLotteryDrawEventId(long lotteryDrawEventId) {
        return "lottery_draw_event::" + lotteryDrawEventId + "::candidates";
    }
}
