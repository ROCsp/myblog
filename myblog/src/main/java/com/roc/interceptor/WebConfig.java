//package com.roc.interceptor;
//
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * 拦截配置
// * 配置拦截哪些页面，放行哪些页面
// */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/admin/**") //配置拦截页面
//                .excludePathPatterns("/admin") //放行页面
//                .excludePathPatterns("/admin/login");
//    }
//}
