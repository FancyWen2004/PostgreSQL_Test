package com.kun.common;

import com.kun.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptorHandler implements HandlerInterceptor {
    /**
     * 前置拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 放行/api/user/下的所有请求
        if (request.getRequestURI().contains("/api/**")) {
            return true;
        }

        //判断如果请求的类不是HandlerMethod，直接通行。
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //判断如果请求的类是swagger的控制器，直接通行。
        if (("springfox.documentation.swagger.web.ApiResourceController").equals(handlerMethod.getBean().getClass().getName())) {
            return true;
        }

        // 放行OPTIONS请求
        if ((Constant.OPTIONS).equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        return true;
    }
}