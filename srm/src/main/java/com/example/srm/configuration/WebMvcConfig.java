package com.example.srm.configuration;

import com.example.srm.Interceptor.CrosIntercceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Resource
    private CrosIntercceptor cros;

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cros).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
