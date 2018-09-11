package com.guomzh.onlineq.util;

/**
 * @author zgm
 * @date 2018/9/9 23:30
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT_QUEUE = "EVENT_QUEUE";

    //关注列表key
    private static String BIZ_FOLLOWER = "FOLLOWER";
    private static String BIZ_FOLLOWEE = "FOLLOWEE";

    public static String getLikeKey(int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENT_QUEUE;
    }

    //每个实体的关注粉丝
    public static String getFollowerKey(int entityType, int entityId){
        return BIZ_FOLLOWER+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    //每个用户他关注的某类实体，例如他关注了问题type，这个key对应的value就会存他关注的各种问题
    public static String getFolloweeKey(int userId, int entityType){
        return BIZ_FOLLOWEE+SPLIT+String.valueOf(userId)+SPLIT+String.valueOf(entityType);
    }
}
