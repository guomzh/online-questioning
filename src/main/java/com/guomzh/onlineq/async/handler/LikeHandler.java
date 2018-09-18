package com.guomzh.onlineq.async.handler;

import com.guomzh.onlineq.async.EventHandler;
import com.guomzh.onlineq.async.EventModel;
import com.guomzh.onlineq.async.EventType;
import com.guomzh.onlineq.domain.Message;
import com.guomzh.onlineq.domain.User;
import com.guomzh.onlineq.service.MessageService;
import com.guomzh.onlineq.service.UserService;
import com.guomzh.onlineq.util.OnlineQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zgm
 * @date 2018/9/10 12:40
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void doHandle(EventModel model) {
        //如果是自己给自己点赞，就不给自己发私信了
        if (model.getEntityOwnerId() == model.getActorId()) {
            return;
        }
        Message message = new Message();
        message.setFromId(OnlineQUtil.LIKE_USER_ID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户 " + user.getName() + " 赞了你的评论, <a href=\"http://117.48.200.9/question/" + model.getExt("questionId") + "\">点击查看该问题</a>");
        message.setConversationId(message.getConversationId());
        messageService.addMessage(message);
    }

    //本handler只处理点赞（like）类型的消息
    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
