package com.kun.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 参数拦截器
@Slf4j
public class CommonInterceptor implements HandlerInterceptor {
    // 前置处理方法，在请求到达 Controller 之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 可以在这里对请求参数进行校验, 比如判断是否包含必要的参数
        // 比如判断请求中是否包含名为 "token" 的参数
        // String paramValue = request.getParameter("token");
        // log.info("paramValue: {}" , paramValue);
        // if (paramValue == null || "".equals(paramValue)) {
        //     response.getWriter().write("请求中缺少必要的 token 参数");
        //     return false;
        // }
        return true;
    }

    // 后置处理方法，在 Controller 处理完请求后，视图渲染之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 可以在这里对返回的数据进行处理, 比如添加签名等
    }

    // 完成后处理方法，在整个请求处理完成后，视图渲染之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 可以在这里进行一些资源清理等操作
    }
}