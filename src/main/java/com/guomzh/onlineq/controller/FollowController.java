package com.guomzh.onlineq.controller;

import com.guomzh.onlineq.async.EventModel;
import com.guomzh.onlineq.async.EventProducer;
import com.guomzh.onlineq.async.EventType;
import com.guomzh.onlineq.domain.EnvContext;
import com.guomzh.onlineq.domain.Question;
import com.guomzh.onlineq.domain.User;
import com.guomzh.onlineq.domain.ViewObject;
import com.guomzh.onlineq.service.CommentService;
import com.guomzh.onlineq.service.FollowService;
import com.guomzh.onlineq.service.QuestionService;
import com.guomzh.onlineq.service.UserService;
import com.guomzh.onlineq.util.OnlineQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zgm
 * @date 2018/9/11 13:03
 */
@Controller
public class FollowController {
    @Autowired
    private FollowService followService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EnvContext envContext;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/followUser"}, method = {RequestMethod.POST})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        if (envContext.getUser() == null) {
            return OnlineQUtil.getJSONString(999);
        }
        boolean ret = followService.follow(envContext.getUser().getId(), OnlineQUtil.ENTITY_USER, userId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(envContext.getUser().getId())
                .setEntityId(userId)
                .setEntityOwnerId(userId)
                .setEntityType(OnlineQUtil.ENTITY_USER));
        return OnlineQUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(envContext.getUser().getId(), OnlineQUtil.ENTITY_USER)));
    }

    @ResponseBody
    @RequestMapping(path = {"/unfollowUser"}, method = {RequestMethod.POST})
    public String unfollowUser(@RequestParam("userId") int userId) {
        if (envContext.getUser() == null) {
            return OnlineQUtil.getJSONString(999);
        }
        boolean ret = followService.unFollow(envContext.getUser().getId(), OnlineQUtil.ENTITY_USER, userId);
        return OnlineQUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(envContext.getUser().getId(), OnlineQUtil.ENTITY_USER)));
    }

    @ResponseBody
    @RequestMapping(path = {"/followQuestion"}, method = {RequestMethod.POST})
    public String followQuestion(@RequestParam("questionId") int questionId) {
        if (envContext.getUser() == null) {
            return OnlineQUtil.getJSONString(999);
        }

        Question question = questionService.getById(questionId);
        if (question == null) {
            return OnlineQUtil.getJSONString(1, "所关注的问题不存在");
        }

        boolean ret = followService.follow(envContext.getUser().getId(), OnlineQUtil.ENTITY_QUESTION, questionId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(envContext.getUser().getId())
                .setEntityId(questionId)
                .setEntityOwnerId(question.getUserId())
                .setExt("questionTitle", question.getTitle())
                .setEntityType(OnlineQUtil.ENTITY_QUESTION));
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", envContext.getUser().getHeadUrl());
        info.put("name", envContext.getUser().getName());
        info.put("id", envContext.getUser().getId());
        info.put("count", followService.getFollowerCount(OnlineQUtil.ENTITY_QUESTION, questionId));
        return OnlineQUtil.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(path = {"/unfollowQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId) {
        if (envContext.getUser() == null) {
            return OnlineQUtil.getJSONString(999);
        }

        Question question = questionService.getById(questionId);
        if (question == null) {
            return OnlineQUtil.getJSONString(1, "所关注的问题不存在");
        }

        boolean ret = followService.unFollow(envContext.getUser().getId(), OnlineQUtil.ENTITY_QUESTION, questionId);
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", envContext.getUser().getHeadUrl());
        info.put("name", envContext.getUser().getName());
        info.put("id", envContext.getUser().getId());
        info.put("count", followService.getFollowerCount(OnlineQUtil.ENTITY_QUESTION, questionId));
        return OnlineQUtil.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(path = {"/user/{userId}/followees"}, method = {RequestMethod.GET})
    public String followees(Model model, @PathVariable("userId") int userId) {
        List<Integer> followeesIds = followService.getFollewees(userId, OnlineQUtil.ENTITY_USER, 0, 10);
        if (envContext.getUser() != null) {
            model.addAttribute("followees", getUsersInfo(envContext.getUser().getId(), followeesIds));
        } else {
            model.addAttribute("followees", getUsersInfo(0, followeesIds));
        }
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId, OnlineQUtil.ENTITY_USER));
        model.addAttribute("curUser", userService.getUser(userId));
        return "followees";
    }

    @RequestMapping(path = {"/user/{userId}/followers"}, method = {RequestMethod.GET})
    public String followers(Model model, @PathVariable("userId") int userId) {
        List<Integer> followersIds = followService.getFollowers(OnlineQUtil.ENTITY_USER, userId, 0, 10);
        if (envContext.getUser() != null) {
            model.addAttribute("followers", getUsersInfo(envContext.getUser().getId(), followersIds));
        } else {
            model.addAttribute("followers", getUsersInfo(0, followersIds));
        }
        model.addAttribute("followerCount", followService.getFollowerCount(OnlineQUtil.ENTITY_USER, userId));
        model.addAttribute("curUser", userService.getUser(userId));
        return "followers";
    }

    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> userIds) {
        List<ViewObject> userInfos = new ArrayList<>();
        for (Integer userId : userIds) {
            User user = userService.getUser(userId);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("followerCount", followService.getFollowerCount(OnlineQUtil.ENTITY_USER, userId));
            vo.set("followeeCount", followService.getFolloweeCount(userId, OnlineQUtil.ENTITY_USER));
            vo.set("commentCount", commentService.getUserCommentCount(userId));
            if (localUserId != 0) {
                vo.set("followed", followService.isFollower(localUserId, OnlineQUtil.ENTITY_USER, userId));
            } else {
                vo.set("followed", false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }
}
