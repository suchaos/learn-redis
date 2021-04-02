package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 发送邮件任务 -- redis list -- BRPOP
 *
 * @author suchao
 * @date 2021/3/30
 */
@Service
public class SendMailService {

    public static final String SEND_MAIL_TASK_QUEUE = "send_mail_task_queue";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 让发送邮件进入任务队列
     *
     * @param sendMailTask sendMailTask
     */
    public void enqueueSendMailTask(String sendMailTask) {
        stringRedisTemplate.opsForList().leftPush(SEND_MAIL_TASK_QUEUE, sendMailTask);
    }

    /**
     * 阻塞获取发送邮件任务
     *
     * @return sendMailTask
     */
    public String takeSendMailTask() {
        return stringRedisTemplate.opsForList().rightPop(SEND_MAIL_TASK_QUEUE, 5, TimeUnit.SECONDS);
    }
}
