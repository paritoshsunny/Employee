package com.java.sunny.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

import com.java.sunny.service.AddressService;

@Configuration
@EnableCaching
public class CacheConfiguration {
	
	Logger logger = LoggerFactory.getLogger(AddressService.class);
	
	
	CacheManagerCustomizer<ConcurrentMapCacheManager> customizer(){
		return new ConcurrentCustomizer();
	}
	
	class ConcurrentCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager>{

		@Override
		public void customize(ConcurrentMapCacheManager cacheManager) {
			
			cacheManager.setAllowNullValues(false);
			cacheManager.setStoreByValue(true);
			logger.trace("Inside CacheManagerCustomizer");
		}
		
	}
}
