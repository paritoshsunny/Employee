package com.java.sunny.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.java.sunny.interceptor.InterceptorForEmployee;
import com.java.sunny.interceptor.IpAddressAndBrowserInfoInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new IpAddressAndBrowserInfoInterceptor());
		registry.addInterceptor(new InterceptorForEmployee()).addPathPatterns("/emp/**");
	}

	
}
