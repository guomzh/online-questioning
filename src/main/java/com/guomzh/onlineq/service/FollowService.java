package com.guomzh.onlineq.service;

import com.guomzh.onlineq.service.Redis.RedisAdapter;
import com.guomzh.onlineq.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zgm
 * @date 2018/9/11 10:34
 */
@Service
public class FollowService {
    @Autowired
    private RedisAdapter redisAdapter;

    public boolean follow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();

        Jedis jedis = redisAdapter.getJedis();
        Transaction tx = redisAdapter.multi(jedis);
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> ret = redisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    public boolean unFollow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Jedis jedis = redisAdapter.getJedis();
        Transaction tx = redisAdapter.multi(jedis);
        tx.zrem(followerKey, String.valueOf(userId));
        tx.zrem(followeeKey, String.valueOf(entityId));
        List<Object> ret = redisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    private List<Integer> getIdsFromSet(Set<String> idSet) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idSet) {
            ids.add(Integer.valueOf(str));
        }
        return ids;
    }

    public List<Integer> getFollewers(int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(redisAdapter.zrevrange(followerKey, 0, count));
    }

    public List<Integer> getFollewers(int entityType, int entityId, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(redisAdapter.zrevrange(followerKey, offset, offset + count));
    }

    public List<Integer> getFollewees(int userId, int entityType, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(redisAdapter.zrevrange(followeeKey, 0, count));
    }

    public List<Integer> getFollewees(int userId, int entityType, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(redisAdapter.zrevrange(followeeKey, offset, offset + count));
    }

    public long getFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return redisAdapter.zcard(followerKey);
    }

    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisAdapter.zcard(followeeKey);
    }

    public boolean isFollower(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return redisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }
}
