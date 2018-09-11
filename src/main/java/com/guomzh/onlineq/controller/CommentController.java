package com.guomzh.onlineq.controller;

import com.guomzh.onlineq.async.EventModel;
import com.guomzh.onlineq.async.EventProducer;
import com.guomzh.onlineq.async.EventType;
import com.guomzh.onlineq.domain.Comment;
import com.guomzh.onlineq.domain.EnvContext;
import com.guomzh.onlineq.service.CommentService;
import com.guomzh.onlineq.service.QuestionService;
import com.guomzh.onlineq.util.OnlineQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author zgm
 * @date 2018/9/9 7:17
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentService commentService;

    @Autowired
    private EnvContext envContext;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            if (envContext.getUser() != null) {
                comment.setUserId(envContext.getUser().getId());
            } else {
                //comment.setUserId(OnlineQUtil.ANONYMOUS_USER_ID);
                return "redirect:/loginReg";
            }
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(OnlineQUtil.ENTITY_QUESTION);
            commentService.addComment(comment);
            //更新评论数
            //1786104060@qq.com
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(count, comment.getEntityId());
            eventProducer.fireEvent(new EventModel(EventType.COMMENT)
                    .setExt("username",envContext.getUser().getName())
                    .setEntityId(questionId)
                    .setExt("email", "guom_zh@qq.com")
            );

        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}
