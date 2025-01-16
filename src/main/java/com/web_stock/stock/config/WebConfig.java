package com.web_stock.stock.config;

import com.web_stock.stock.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**") // 인터셉터를 적용할 URL 패턴
//                .excludePathPatterns("/login","/about","/stocklist/**","/","/images/**","/signup","/resources/**"); // 제외할 패턴
                .excludePathPatterns("/**"); // 제외할 패턴

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}