package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 博客
 *
 * @author suchao
 * @date 2021/3/29
 */
@Service
public class BlogService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据发号器获得一个 id
     *
     * @return
     */
    public long produceBlogId() {
        return stringRedisTemplate.opsForValue().increment("blog_id_counter");
    }

    /**
     * 发布博客
     *
     * @param id
     * @param title
     * @param content
     * @param author
     * @param time
     */
    public boolean publishBlog(long id, String title, String content, String author, String time) {
        if (stringRedisTemplate.opsForHash().hasKey(getBlogKeyById(id), BLOG_PARTS.TITLE.getName())) {
            return false;
        }
        Map<String, String> blog = new HashMap<>();
        blog.put(BLOG_PARTS.TITLE.getName(), title);
        blog.put(BLOG_PARTS.CONTENT.getName(), content);
        blog.put(BLOG_PARTS.AUTHOR.getName(), author);
        blog.put(BLOG_PARTS.TITLE.getName(), time);
        blog.put(BLOG_PARTS.CONTENT_LENGTH.getName(), String.valueOf(content.length()));
        blog.put(BLOG_PARTS.LIKE_COUNTS.getName(), "0");
        blog.put(BLOG_PARTS.VIEW_COUNTS.getName(), "0");
        stringRedisTemplate.opsForHash().putAll(getBlogKeyById(id), blog);
        return true;
    }

    /**
     * 更新博客内容与标题
     *
     * @param id
     * @param title
     * @param content
     */
    public void updateBlog(long id, String title, String content) {
        Map<String, String> blog = new HashMap<>();
        if (!StringUtils.isEmpty(title)) {
            blog.put(BLOG_PARTS.TITLE.getName(), title);
        }
        if (!StringUtils.isEmpty(content)) {
            blog.put(BLOG_PARTS.CONTENT.getName(), content);
            blog.put(BLOG_PARTS.CONTENT_LENGTH.getName(), String.valueOf(content.length()));
        }
        stringRedisTemplate.opsForHash().putAll(getBlogKeyById(id), blog);
    }

    /**
     * 点赞博客
     *
     * @param id
     */
    public void likeBlog(long id) {
        stringRedisTemplate.opsForHash().increment(getBlogKeyById(id),
                BLOG_PARTS.LIKE_COUNTS.getName(), 1);
    }

    /**
     * 预览博客
     *
     * @param id
     * @return
     */
    public String previewBlog(long id) {
        //return stringRedisTemplate.opsForHash().
        return null;
    }

    /**
     * 根据 id 获取博客
     *
     * @param id
     * @return
     */
    public Map<String, String> getBlog(long id) {
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(getBlogKeyById(id));
        Map<String, String> blog = entries.entrySet().stream().collect(
                Collectors.toMap(k -> (String) k.getKey(), v -> (String) v.getValue()));
        // 同时给浏览数加一
        incrementBlogViewCount(id);

        return blog;
        //return stringRedisTemplate.opsForHash().multiGet(getBlogKeyById(id), getBlogKeysById(id));
    }

    /**
     * 增加浏览次数
     *
     * @param id id
     */
    private void incrementBlogViewCount(long id) {
        stringRedisTemplate.opsForHash().increment(getBlogKeyById(id),
                BLOG_PARTS.VIEW_COUNTS.getName(), 1);
    }

    /**
     * 拼接 id
     *
     * @param id
     * @return
     */
    private String getBlogKeyById(long id) {
        return "article::" + id;
    }

//    /**
//     * 拼接 blog hash Key
//     *
//     * @param id
//     * @return
//     */
//    public Collection<Object> getBlogKeysById(long id) {
//        Collection<Object> blogHashKey = new ArrayList<>(4);
//        blogHashKey.add("article:" + id + "title");
//        blogHashKey.add("article:" + id + "content");
//        blogHashKey.add("article:" + id + "author");
//        blogHashKey.add("article:" + id + "time");
//        blogHashKey.add("article:" + id + "content_length");
//        blogHashKey.add("article:" + id + "likes");
//        return blogHashKey;
//    }
}

enum BLOG_PARTS {
    /**
     * title
     */
    TITLE("title"),

    /**
     * content
     */
    CONTENT("content"),
    AUTHOR("author"),
    TIME("time"),
    CONTENT_LENGTH("content_length"),
    LIKE_COUNTS("like_numbers"),
    VIEW_COUNTS("view_counts");

    private final String name;

    BLOG_PARTS(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
