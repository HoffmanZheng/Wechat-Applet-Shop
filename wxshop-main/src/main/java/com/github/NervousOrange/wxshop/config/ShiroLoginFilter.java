package com.github.NervousOrange.wxshop.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@Component
@WebFilter(filterName = "ShiroLoginFilter", urlPatterns = "/*")
public class ShiroLoginFilter extends FormAuthenticationFilter {

    /**
     * 对于非登录模块的接口，如果用户没有登录，就返回 401，不进行重定向
     *
     * @param request  http 请求
     * @param response http 响应
     * @return true 继续往下执行，false 该请求过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    public boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        return false;
    }
}
