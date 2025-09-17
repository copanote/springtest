package com.copanote.SpringTest.web.config;

import com.copanote.SpringTest.web.argumentresolver.LoginArgumentResolver;
import com.copanote.SpringTest.web.filter.LogFilter;
import com.copanote.SpringTest.web.interceptor.LogInterceptor;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    /**
     *  필터를 등록하는 방법은 여러가지가 있지만, 스프링부트를 사용한다면
     *  FilterRegistrationBean을 사용해서 등록하면 된다.
     */
    @Bean
    public FilterRegistrationBean<Filter> logFilter() {
        FilterRegistrationBean<Filter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new LogFilter());
        frb.setOrder(1);
        frb.addUrlPatterns("/*");
        return frb;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}
