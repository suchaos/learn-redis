package com.suchao.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * session -- hash 结构
 *
 * @author suchao
 * @date 2021/3/30
 */
@Service
public class SessionService {

    public static final String SESSIONS = "sessions";
    public static final String SESSIONS_EXPIRE_TIME = "sessions::expire_time";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 检查 token 是否有效
     *
     * @param token token
     * @return true or false
     */
    public boolean isSessionValid(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        // 拿到的 session 可能是一个 json 字符串，这里只存一个 userId
        String session = (String) stringRedisTemplate.opsForHash().
                get(SESSIONS, getSessionHashKeyByToken(token));
        if (StringUtils.isEmpty(session)) {
            return false;
        }

        // 检查 session 是否过期, expireTime 格式是 "2007-12-03T10:15:30"
        String expireTime = (String) stringRedisTemplate.opsForHash()
                .get(SESSIONS_EXPIRE_TIME, getSessionHashKeyByToken(token));

        if (StringUtils.isEmpty(expireTime)) {
            return false;
        }
        if (LocalDateTime.now().isAfter(LocalDateTime.parse(expireTime))) {
            return false;
        }

        // 获取到的 session 不为空，且没有过期
        return true;
    }

    /**
     * 用户成功登录之后，初始化一个 session
     *
     * @param userId 用户id
     */
    public void initSession(String token, long userId) {
        stringRedisTemplate.opsForHash()
                .put(SESSIONS, getSessionHashKeyByToken(token), String.valueOf(userId));

        // 5 hours 的过期时间
        stringRedisTemplate.opsForHash()
                .put(SESSIONS_EXPIRE_TIME, getSessionHashKeyByToken(token),
                        LocalDateTime.now().plusHours(5).toString());
    }

    private String getSessionHashKeyByToken(String token) {
        return "session::" + token;
    }
}
