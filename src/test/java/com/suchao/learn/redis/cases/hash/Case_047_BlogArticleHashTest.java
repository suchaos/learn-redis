package com.suchao.learn.redis.cases.hash;

import com.suchao.learn.redis.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Map;

/**
 * 博客的发布
 * 使用 hash 结构进行重构
 *
 * @author suchao
 * @date 2021/3/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Case_047_BlogArticleHashTest {

    @Autowired
    private BlogService blogService;

    @Test
    public void useAsBlogPublish() {
        long blogId = blogService.produceBlogId();
        // 发布博客
        blogService.publishBlog(
                blogId,
                "how to learn redis in 3 days!",
                "自己去看书和官网",
                "suchao",
                LocalDate.now().toString());

        // 查看博客
        Map<String, String> blog = blogService.getBlog(blogId);
        log.info("blog 内容: {}", blog);

        // 修改博客
        blogService.updateBlog(blogId, null, "修改之后的学习内容");

        // 查看博客并点赞
        for (int i = 0; i < 10; i++) {
            blogService.getBlog(blogId);
            blogService.likeBlog(blogId);
        }

        // 自己查看浏览数与点赞数
        blog = blogService.getBlog(blogId);
        log.info("blog 内容: {}", blog);
    }
}
