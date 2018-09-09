package com.guomzh.onlineq.service;

import com.guomzh.onlineq.dao.LoginTicketDao;
import com.guomzh.onlineq.dao.UserDao;
import com.guomzh.onlineq.domain.LoginTicket;
import com.guomzh.onlineq.domain.User;
import com.guomzh.onlineq.util.OnlineQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    public User getUser(int id) {
        return userDao.selectById(id);
    }

    public User getUserByName(String name){
        return userDao.selectByName(name);
    }

    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空！");
            return map;
        }
        User user = userDao.selectByName(username);
        if (user == null) {
            map.put("msg", "用户名不存在！");
            return map;
        }
        if (!OnlineQUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确！");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        Date now = new Date();
        now.setTime(3600 * 24 * 15 * 1000 + now.getTime());
        ticket.setUserId(userId);
        ticket.setExpired(now);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket, 1);
    }

    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空！");
            return map;
        }
        User user = userDao.selectByName(username);
        if (user != null) {
            map.put("msg", "用户名已经被注册！");
            return map;
        }
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
        user.setPassword(OnlineQUtil.MD5(password + user.getSalt()));
        userDao.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }
}
