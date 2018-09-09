package com.guomzh.onlineq.controller;

import com.guomzh.onlineq.domain.EnvContext;
import com.guomzh.onlineq.service.LikeService;
import com.guomzh.onlineq.util.OnlineQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zgm
 * @date 2018/9/9 23:24
 */
@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private EnvContext envContext;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (envContext.getUser() == null) {
            return OnlineQUtil.getJSONString(999);
        }
        long likeCount = likeService.like(envContext.getUser().getId(), OnlineQUtil.ENTITY_COMMENT, commentId);
        return OnlineQUtil.getJSONString(0, String.valueOf(likeCount) + "赞同");
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId) {
        if (envContext.getUser() == null) {
            return OnlineQUtil.getJSONString(999);
        }
        long likeCount = likeService.disLike(envContext.getUser().getId(), OnlineQUtil.ENTITY_COMMENT, commentId);
        return OnlineQUtil.getJSONString(0, String.valueOf(likeCount) + "赞同");
    }

}
