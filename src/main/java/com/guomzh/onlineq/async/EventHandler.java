package com.guomzh.onlineq.async;

import java.util.List;

/**
 * @author zgm
 * @date 2018/9/10 11:51
 */
public interface EventHandler {

    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
