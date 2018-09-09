package com.guomzh.onlineq.intercepter;

import com.guomzh.onlineq.domain.EnvContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zgm
 * @date 2018/9/6 18:54
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private EnvContext envContext;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (envContext.getUser() == null) {
            httpServletResponse.sendRedirect("/loginReg?next="+httpServletRequest.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
