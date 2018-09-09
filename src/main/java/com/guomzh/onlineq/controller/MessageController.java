package com.guomzh.onlineq.controller;

import com.guomzh.onlineq.domain.EnvContext;
import com.guomzh.onlineq.domain.Message;
import com.guomzh.onlineq.domain.User;
import com.guomzh.onlineq.domain.ViewObject;
import com.guomzh.onlineq.service.MessageService;
import com.guomzh.onlineq.service.UserService;
import com.guomzh.onlineq.util.OnlineQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zgm
 * @date 2018/9/9 10:21
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private EnvContext envContext;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {

        try {
            if (envContext.getUser() == null) {
                return "redirect:/loginReg";
            }
            int userId = envContext.getUser().getId();
            List<Message> conversationList = messageService.getConversationList(userId, 0, 10);
            List<ViewObject> conversations = new ArrayList<>();
            for (Message message : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                int targetId = message.getFromId() == userId ? message.getToId() : message.getFromId();
                vo.set("user", userService.getUser(targetId));
                vo.set("unread", messageService.getConversationUnreadCount(userId,message.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("获取消息详情失败 " + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model,
                                        @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", userService.getUser(message.getFromId()));
                messages.add(vo);
            }
            messageService.updateHasRead(envContext.getUser().getId(),conversationId);
            model.addAttribute("conversations", messages);
        } catch (Exception e) {
            logger.error("获取详情失败失败 " + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (envContext.getUser() == null) {
                return OnlineQUtil.getJSONString(999, "未登录");
            }
            User user = userService.getUserByName(toName);
            if (user == null) {
                return OnlineQUtil.getJSONString(1, "用户不存在");
            }
            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(envContext.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            message.setConversationId(message.getConversationId());
            messageService.addMessage(message);
            return OnlineQUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("发送私信失败 " + e.getMessage());
            return OnlineQUtil.getJSONString(1, "发送私信失败");
        }
    }
}
