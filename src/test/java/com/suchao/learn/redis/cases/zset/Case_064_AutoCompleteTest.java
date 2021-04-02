package com.suchao.learn.redis.cases.zset;

import com.suchao.learn.redis.service.AutoCompleteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * 自动补全案例
 *
 * @author suchao
 * @date 2021/4/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_064_AutoCompleteTest {

    @Autowired
    private AutoCompleteService autoCompleteService;

    @Test
    public void autoComplete() {
        // 训练数据
        autoCompleteService.search("我爱大家");
        autoCompleteService.search("我喜欢学习redis");
        autoCompleteService.search("我很喜欢一个城市");
        autoCompleteService.search("我不喜欢玩儿");
        autoCompleteService.search("我喜欢学习spark");

        // 查看结果
        Set<String> autoCompleteList = autoCompleteService.getAutoCompleteList("我", 0, 2);
        log.info("对于 我 的自动补全: {}", autoCompleteList);

        autoCompleteList = autoCompleteService.getAutoCompleteList("我喜", 0, 2);
        log.info("对于 我喜 的自动补全: {}", autoCompleteList);
    }
}
