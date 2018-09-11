package com.guomzh.onlineq.controller;

import com.guomzh.onlineq.domain.EnvContext;
import com.guomzh.onlineq.domain.Question;
import com.guomzh.onlineq.domain.User;
import com.guomzh.onlineq.domain.ViewObject;
import com.guomzh.onlineq.service.CommentService;
import com.guomzh.onlineq.service.FollowService;
import com.guomzh.onlineq.service.QuestionService;
import com.guomzh.onlineq.service.UserService;
import com.guomzh.onlineq.util.OnlineQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService qeustionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FollowService followService;

    @Autowired
    private EnvContext envContext;

    @RequestMapping(path = {"/user/{userId}"} , method = {RequestMethod.GET})
    public String userIndex(Model model, @PathVariable("userId") int userId){
        List<ViewObject> vos = getQuestions(userId,0,10);
        model.addAttribute("vos", vos);
        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCount", commentService.getUserCommentCount(userId));
        vo.set("followerCount", followService.getFollowerCount(OnlineQUtil.ENTITY_USER, userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, OnlineQUtil.ENTITY_USER));
        if (envContext.getUser() != null) {
            vo.set("followed", followService.isFollower(envContext.getUser().getId(), OnlineQUtil.ENTITY_USER, userId));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);
        return "profile";
    }

    @RequestMapping(path={"/","index"}, method = {RequestMethod.GET})
    public String index(Model model){
        List<ViewObject> vos = getQuestions(0,0,10);
        model.addAttribute("vos", vos);
        return "index";
    }

    private List<ViewObject> getQuestions(int userId, int offset,int limit){
        List<Question> questionList=qeustionService.getLatestQuestion(userId,offset,limit);
        List<ViewObject> vos =new ArrayList<>();
        for(Question question:questionList){
            ViewObject vo =new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vo.set("followCount", followService.getFollowerCount(OnlineQUtil.ENTITY_QUESTION, question.getId()));
            vos.add(vo);
        }
        return vos;
    }
}
