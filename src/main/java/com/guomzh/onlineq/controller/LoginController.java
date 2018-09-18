package com.guomzh.onlineq.controller;

import com.guomzh.onlineq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zgm
 * @date 2018/9/6 7:41
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/login"}, method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberMe", defaultValue = "false") boolean rememberMe,
                        HttpServletResponse response) {
        try {
            Map<String, String> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                if(rememberMe){
                    cookie.setMaxAge(Integer.MAX_VALUE);
                }
                response.addCookie(cookie);
                if(!StringUtils.isEmpty(next)){
                    return "redirect:"+next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("登录出现异常. " + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/reg_prepare"}, method = {RequestMethod.GET})
    public String reg_prepare( Model model,
                               @RequestParam(value = "username",required = false) String username,
                               @RequestParam(value = "password",required = false) String password){
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        model.addAttribute("msg","请输入注册邮箱！");
        return "register";
    }

    @RequestMapping(path = {"/reg"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("email") String email,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      HttpServletResponse response) {
        try {
            Map<String, String> map = userService.register(username, password,email);
            if( map.containsKey("msg")){
                model.addAttribute("msg", map.get("msg"));
                return "register";
            } else if(map.containsKey("toVerify")){
                return "register_tip";
            }
        } catch (Exception e) {
            logger.error("注册出现异常. " + e.getMessage());
            return "login";
        }
        return "index";
    }

    @RequestMapping(path = {"/regVerify"}, method = {RequestMethod.GET})
    public String regVerify(@RequestParam(value = "p")String p){
          if(p==null){
              return "index";
          }
          if(userService.emailVerify(p)){
              return "register_success_tip";
          }
          else{
              return "register_fail_tip";
          }
    }

    @RequestMapping(path = {"/loginReg"}, method = {RequestMethod.GET})
    public String loginReg(@RequestParam(value = "next", required = false) String next,
                           Model model) {
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket")String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
