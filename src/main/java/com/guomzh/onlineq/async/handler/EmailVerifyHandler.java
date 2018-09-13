package com.guomzh.onlineq.async.handler;

import com.guomzh.onlineq.async.EventHandler;
import com.guomzh.onlineq.async.EventModel;
import com.guomzh.onlineq.async.EventType;
import com.guomzh.onlineq.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zgm
 * @date 2018/9/13 21:37
 */
@Component
public class EmailVerifyHandler implements EventHandler {

    @Autowired
    private MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        Map<String ,Object> map=new HashMap<>();
        map.put("url","http://127.0.0.1:8080/regVerify?p="+model.getExt("register_ticket"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"),"<我的知乎——在线问答平台>注册激活邮件",
                "mails/register_email.html", map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.REGISTER);
    }
}
