package com.guomzh.onlineq.service;

import com.guomzh.onlineq.dao.QuestionDao;
import com.guomzh.onlineq.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private SensitiveService sensitiveService;

    public int addQuestion(Question question) {
        //html过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public Question getById(int id){
        return questionDao.selectById(id);
    }

    public void updateCommentCount(int commentCount,int id){
        questionDao.updateCommentCount(commentCount,id);
    }

    public List<Question> getLatestQuestion(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }
}
