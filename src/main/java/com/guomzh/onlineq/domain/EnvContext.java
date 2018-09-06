package com.guomzh.onlineq.domain;

import org.springframework.stereotype.Component;

/**
 * @author zgm
 * @date 2018/9/6 16:29
 */
@Component
public class EnvContext {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
