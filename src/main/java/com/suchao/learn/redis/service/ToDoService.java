package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * oa -- 待办事项 -- redis list
 *
 * @author suchao
 * @date 2021/3/30
 */
@Service
public class ToDoService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加待办事项到最后
     *
     * @param userId    userId
     * @param toDoEvent toDoEvent
     */
    public void addTodoEvent(long userId, String toDoEvent) {
        stringRedisTemplate.opsForList().rightPush(getToDoEventKeyByUserId(userId), toDoEvent);
    }

    /**
     * 添加待办事项到 pivot 之前
     *
     * @param userId    userId
     * @param toDoEvent toDoEvent
     * @param pivot     pivot
     */
    public void addTodoEvent(long userId, String toDoEvent, String pivot) {
        stringRedisTemplate.opsForList().leftPush(getToDoEventKeyByUserId(userId), pivot, toDoEvent);
    }

    /**
     * 修改指定 index 的待办事项
     *
     * @param userId           userId
     * @param index            index
     * @param updatedToDoEvent updatedToDoEvent
     */
    public void updateTodoEvent(long userId, int index, String updatedToDoEvent) {
        stringRedisTemplate.opsForList().set(getToDoEventKeyByUserId(userId), index, updatedToDoEvent);
    }

    /**
     * 分页查询列表
     *
     * @param userId   userId
     * @param pageNo   pageNo
     * @param pageSize pageSize
     * @return List<String>
     */
    public List<String> findToDoEventsByPage(long userId, int pageNo, int pageSize) {
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = pageNo * pageSize - 1;
        return stringRedisTemplate.opsForList().range(getToDoEventKeyByUserId(userId), startIndex, endIndex);
    }

    /**
     * 完成一个待办事项
     *
     * @param userId    userId
     * @param toDoEvent toDoEvent
     */
    public void finishToDoEvent(long userId, String toDoEvent) {
        stringRedisTemplate.opsForList().remove(getToDoEventKeyByUserId(userId), 0, toDoEvent);
    }


    private String getToDoEventKeyByUserId(long userId) {
        return "todo_event::" + userId;
    }
}
