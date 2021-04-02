package com.suchao.learn.redis.cases.list;

import com.suchao.learn.redis.service.ToDoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 待办事项
 *
 * @author suchao
 * @date 2021/3/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_051_ToDoTest {

    @Autowired
    private ToDoService toDoService;

    @Test
    public void todo() {
        long userId = 123;

        // 添加 20 个待办事项
        for (int i = 0; i < 20; i++) {
            toDoService.addTodoEvent(userId, "第" + (i + 1) + "个待办事项");
        }

        // 查询第一页
        int pageNo = 1;
        int pageSize = 10;
        List<String> toDoEvents = toDoService.findToDoEventsByPage(userId, pageNo, pageSize);
        toDoEvents.forEach(log::info);

        // 插入待办事项
        String targetToDoEvent = toDoEvents.get(ThreadLocalRandom.current().nextInt(toDoEvents.size()));
        toDoService.addTodoEvent(userId, "新增加的待办事项", targetToDoEvent);
        addDelimiter();

        // 再次查询
        toDoEvents = toDoService.findToDoEventsByPage(userId, pageNo, pageSize);
        toDoEvents.forEach(log::info);
        addDelimiter();

        // 修改待办事项
        toDoService.updateTodoEvent(userId, ThreadLocalRandom.current().nextInt(10), "修改的待办事项");

        toDoEvents = toDoService.findToDoEventsByPage(userId, pageNo, pageSize);
        toDoEvents.forEach(log::info);
        addDelimiter();

        // 完成待办事项
        toDoService.finishToDoEvent(userId, "修改的待办事项");

        toDoEvents = toDoService.findToDoEventsByPage(userId, pageNo, pageSize);
        toDoEvents.forEach(log::info);
        addDelimiter();
    }

    private void addDelimiter() {
        log.info("=========================================================");
    }


}
