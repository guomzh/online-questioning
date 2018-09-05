package com.guomzh.onlineq.service;

import com.guomzh.onlineq.dao.QuestionDao;
import com.guomzh.onlineq.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QeustionService {
    @Autowired
    private QuestionDao questionDao;

    public List<Question> getLatestQuestion(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }
}
