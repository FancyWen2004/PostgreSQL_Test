package com.kun.common;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Date;

// 使用 @WebFilter 注解指定过滤器的拦截路径，这里拦截所有路径
@WebFilter("/*")
@Slf4j
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化方法，在过滤器被创建时调用, 可以在这里进行一些初始化操作
        // 例如加载配置文件、建立数据库连接等操作
        log.info("TimeFilter 初始化");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 执行过滤逻辑，在请求到达 Servlet 之前执行
        // 可以在这里对请求进行预处理，比如记录请求时间、验证用户身份等操作
        // 示例1：记录请求处理时间
        long startTime = new Date().getTime();
        // 放行请求，让请求继续传递到下一个过滤器或 Servlet
        filterChain.doFilter(servletRequest, servletResponse);
        long endTime = new Date().getTime();
        // 记录请求处理时间
        log.info("本次请求处理时间为：{} ms", endTime - startTime);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // 销毁方法，在过滤器被销毁时调用
        // 可以在这里进行一些清理操作，比如关闭数据库连接等
        log.info("Filter destroy!");
        Filter.super.destroy();
    }
}