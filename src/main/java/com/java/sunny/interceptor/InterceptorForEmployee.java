package com.java.sunny.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterceptorForEmployee implements HandlerInterceptor{

	Logger logger = LoggerFactory.getLogger(InterceptorForEmployee.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.trace("Inside InterceptorForEmployee");
		
//		String ipAddress = request.getRemoteAddr();
//		String browser = request.getHeader("User-Agent");
//		
//		logger.trace(ipAddress+" "+browser);
		
		return true;
	}

	
	
	
}
