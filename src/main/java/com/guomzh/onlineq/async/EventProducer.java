package com.guomzh.onlineq.async;

import com.alibaba.fastjson.JSONObject;
import com.guomzh.onlineq.service.Redis.RedisAdapter;
import com.guomzh.onlineq.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zgm
 * @date 2018/9/10 11:13
 */
@Service
public class EventProducer {
    @Autowired
    private RedisAdapter redisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            redisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
