package com.github.NervousOrange.wxshop.config;

import com.github.NervousOrange.wxshop.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    MyInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        UserContext.setThreadLocalUser(null);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO：所有请求进来都会先进 前置拦截器吗，那不是每次都要去数据库查一下用户信息，这个意义在哪....
        String tel = (String) SecurityUtils.getSubject().getPrincipal();
        if (tel != null) {  // imply that client is already logged
            userService.getUserByTel(tel).ifPresent(UserContext::setThreadLocalUser);
        }
        return true;
    }
}
