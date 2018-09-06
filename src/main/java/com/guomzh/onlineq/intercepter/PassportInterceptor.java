package com.guomzh.onlineq.intercepter;

import com.guomzh.onlineq.dao.LoginTicketDao;
import com.guomzh.onlineq.dao.UserDao;
import com.guomzh.onlineq.domain.EnvContext;
import com.guomzh.onlineq.domain.LoginTicket;
import com.guomzh.onlineq.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zgm
 * @date 2018/9/6 16:22
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EnvContext envContext;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 1) {
                return true;
            }
            User user = userDao.selectById(loginTicket.getUserId());
            envContext.setUser(user);

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
             modelAndView.addObject("user", envContext.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        envContext.clear();
    }
}
