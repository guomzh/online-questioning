package com.guomzh.onlineq.controller;

import com.guomzh.onlineq.domain.Comment;
import com.guomzh.onlineq.domain.EnvContext;
import com.guomzh.onlineq.domain.Question;
import com.guomzh.onlineq.domain.ViewObject;
import com.guomzh.onlineq.service.*;
import com.guomzh.onlineq.util.OnlineQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zgm
 * @date 2018/9/6 23:59
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private EnvContext envContext;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if (envContext.getUser() == null) {
                return OnlineQUtil.getJSONString(999);
            } else {
                question.setUserId(envContext.getUser().getId());
            }
            if (questionService.addQuestion(question) > 0) {
                return OnlineQUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加问题失败" + e.getMessage());
        }
        return OnlineQUtil.getJSONString(1, "失败");
    }

    @RequestMapping(path = {"/question/{id}"} , method = {RequestMethod.GET})
    public String questionDetail(Model model,
                                 @PathVariable("id") int id) {
        Question question = questionService.getById(id);
        List<Comment> commentList = commentService.getCommentsByEntity(id, OnlineQUtil.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);

            if(envContext.getUser()==null){
                vo.set("liked", 0);
            } else{
                vo.set("liked", likeService.getLikeStatus(envContext.getUser().getId(),OnlineQUtil.ENTITY_COMMENT,comment.getId()));
            }
            vo.set("likeCount",likeService.getLikeCount(OnlineQUtil.ENTITY_COMMENT,comment.getId())+"赞同");
            vo.set("user", userService.getUser(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("question", question);
        model.addAttribute("comments", comments);
        if (envContext.getUser() != null) {
            model.addAttribute("followed", followService.isFollower(envContext.getUser().getId(), OnlineQUtil.ENTITY_QUESTION, id));
        } else {
            model.addAttribute("followed", false);
        }
        return "detail";
    }
}
