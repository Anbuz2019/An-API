package com.anbuz.anapibackend.config;

import com.anbuz.anapibackend.interceptor.UserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private UserInterceptor userInterceptor;

    /**
     * 解决跨域访问的问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8001", "http://localhost:8000") // 允许的来源
                .allowCredentials(true) // 允许凭证（Cookie）
                .allowedMethods("*") // 允许的方法
                .allowedHeaders("*"); // 允许的头部
    }

    /**
     * 注册自定义拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/doc.html", "/swagger-resources");
    }
}
