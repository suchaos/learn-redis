package com.suchao.learn.redis.cases.list;

import com.suchao.learn.redis.service.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 邮件发送任务
 *
 * @author suchao
 * @date 2021/3/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_052_SendMailTaskTest {

    @Autowired
    private SendMailService sendMailService;

    @Test
    public void sendMail() {
        String sendMailTask = sendMailService.takeSendMailTask();
        Assert.assertNull(sendMailTask);

        sendMailService.enqueueSendMailTask("第一个邮件发送任务");
        sendMailService.enqueueSendMailTask("第二个邮件发送任务");
        sendMailTask = sendMailService.takeSendMailTask();
        Assert.assertNotNull(sendMailTask);
        Assert.assertEquals("第一个邮件发送任务", sendMailTask);
    }
}
