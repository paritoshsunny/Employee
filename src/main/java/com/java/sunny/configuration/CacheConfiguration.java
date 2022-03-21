package com.java.sunny.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.java.sunny.service.AddressService;


@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport{
	
	Logger logger = LoggerFactory.getLogger(AddressService.class);
	
	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheMaanger());
	}
	
	public net.sf.ehcache.CacheManager ehCacheMaanger() {
		EhCacheManagerFactoryBean bean=new EhCacheManagerFactoryBean();
		bean.setConfigLocation(new ClassPathResource("/Employee/ehcache.xml"));
		bean.setShared(true);
		return bean.getObject();
	
	}
}
