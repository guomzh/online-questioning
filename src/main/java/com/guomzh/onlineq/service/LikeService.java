package com.guomzh.onlineq.service;

import com.guomzh.onlineq.service.Redis.RedisAdapter;
import com.guomzh.onlineq.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zgm
 * @date 2018/9/9 23:25
 */
@Service
public class LikeService {
    @Autowired
    private RedisAdapter redisAdapter;

    public long getLikeCount(int entityType, int entityId){
        String likeKey=RedisKeyUtil.getLikeKey(entityType,entityId);
        return redisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (redisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return redisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        redisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        redisAdapter.srem(disLikeKey, String.valueOf(userId));

        return redisAdapter.scard(likeKey);
    }

    public long disLike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        redisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        redisAdapter.srem(likeKey, String.valueOf(userId));

        return redisAdapter.scard(likeKey);
    }
}
