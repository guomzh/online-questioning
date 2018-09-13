package com.guomzh.onlineq.controller;

import com.guomzh.onlineq.domain.Question;
import com.guomzh.onlineq.domain.ViewObject;
import com.guomzh.onlineq.service.FollowService;
import com.guomzh.onlineq.service.QuestionService;
import com.guomzh.onlineq.service.SearchService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author zgm
 * @date 2018/9/12 21:25
 */
@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchService searchService;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public String addComment(Model model,
                             @RequestParam("q") String keyword,
                             @RequestParam(value = "offset" , defaultValue = "0") int  offset,
                             @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList =searchService.searchQuestion(keyword,offset,count,"<em>","</em>");
            List<ViewObject> vos =new ArrayList<>();
            for(Question question:questionList){
                ViewObject vo =new ViewObject();
                Question q =questionService.getById(question.getId());
                if(question.getContent()!=null){
                    q.setContent(question.getContent());
                }
                if(question.getTitle()!=null){
                    q.setTitle(question.getTitle());
                }
                vo.set("question", q);
                vo.set("user", userService.getUser(q.getUserId()));
                vo.set("followCount", followService.getFollowerCount(OnlineQUtil.ENTITY_QUESTION, q.getId()));
                vos.add(vo);
            }
            model.addAttribute("keyword",keyword);
            model.addAttribute("vos",vos);
        } catch (Exception e) {
            logger.error("搜索失败" + e.getMessage());
        }
        return "result";
    }
}
