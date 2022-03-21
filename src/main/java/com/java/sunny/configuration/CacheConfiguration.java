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
		
		net.sf.ehcache.config.CacheConfiguration employeeList=new net.sf.ehcache.config.CacheConfiguration();
		employeeList.setName("employeeList");
		employeeList.setMaxEntriesLocalHeap(1000);
		employeeList.internalSetTimeToIdle(18000);
		employeeList.setTimeToIdleSeconds(1000);
		
		net.sf.ehcache.config.CacheConfiguration employee=new net.sf.ehcache.config.CacheConfiguration();
		employee.setName("employee");
		employee.setMaxEntriesLocalHeap(1000);
		employee.internalSetTimeToIdle(18000);
		employee.setTimeToIdleSeconds(1000);
		
		net.sf.ehcache.config.CacheConfiguration employeeName=new net.sf.ehcache.config.CacheConfiguration();
		employeeName.setName("employeeName");
		employeeName.setMaxEntriesLocalHeap(1000);
		employeeName.internalSetTimeToIdle(18000);
		employeeName.setTimeToIdleSeconds(1000);
		
		net.sf.ehcache.config.CacheConfiguration employeeSorted=new net.sf.ehcache.config.CacheConfiguration();
		employeeSorted.setName("employeeSorted");
		employeeSorted.setMaxEntriesLocalHeap(1000);
		employeeSorted.internalSetTimeToIdle(18000);
		employeeSorted.setTimeToIdleSeconds(1000);
		
		net.sf.ehcache.config.Configuration config=new net.sf.ehcache.config.Configuration();
		config.addCache(employeeSorted);
		config.addCache(employeeName);
		config.addCache(employee);
		config.addCache(employeeList);
		
		return net.sf.ehcache.CacheManager.newInstance(config);
	
	}
}
