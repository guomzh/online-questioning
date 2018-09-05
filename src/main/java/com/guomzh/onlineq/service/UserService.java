package com.guomzh.onlineq.service;

import com.guomzh.onlineq.dao.UserDao;
import com.guomzh.onlineq.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUser(int id){
        return userDao.selectById(id);
    }
}
