package com.kabu.blog.config;

import com.kabu.blog.handler.LoginInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInteceptor loginInteceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置,前端是8080端口,后端是8888端口,是两个不同的服务
//        registry.addMapping("/**").allowedOrigins("你的前端地址");
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
    //添加拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //模拟拦截test请求
        registry.addInterceptor(loginInteceptor)
                .addPathPatterns("/test").addPathPatterns("/comments/create/change").addPathPatterns("/articles/publish");
    }
}
