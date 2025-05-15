package com.kun.config;

import com.kun.common.AuthInterceptorHandler;
import com.kun.common.CommonInterceptor;
import com.kun.common.MyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    // 配置跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射...");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，并指定拦截路径和排除路径
        registry.addInterceptor(new CommonInterceptor())
                // 拦截所有路径
                //.addPathPatterns("/**")  
                // 排除登录和注册路径
                .excludePathPatterns("/user/login")
                // 测试需要排除所有路径
                .excludePathPatterns("/**");

    }

    // 添加过滤器
//    @Bean
//    public FilterRegistrationBean<MyFilter> myFilterRegistrationBean() {
//        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new MyFilter());
//        // 设置过滤器拦截的路径
//         registrationBean.addUrlPatterns("/*");
//        // 设置过滤器的顺序
//        // 测试需要放行所有路径
//        registrationBean.addUrlPatterns("/**");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
}