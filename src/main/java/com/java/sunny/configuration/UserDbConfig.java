package com.java.sunny.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactrory",
						transactionManagerRef = "userTransactionManager",
						basePackages = "com.java.sunny.repository")
public class UserDbConfig {
	
	@Value("${spring.user.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.user.datasource.username}")
	private String username;
	@Value("${spring.user.datasource.password}")
	private String password;
	@Value("${spring.employee.datasource.jdbcUrl}")
	private String url;
	@Value("${user.poolSize}")
	private Integer poolSize;
	@Value("${user.timeout}")
	private Long timeout;
	@Value("${user.poolName}")
	private String poolName;
	
	@Bean(name = "userDataSource")
	public DataSource dataSource() {
		HikariConfig hikariConfig= new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setMaximumPoolSize(poolSize);
		hikariConfig.setConnectionTimeout(timeout);
		hikariConfig.setPoolName(poolName);
		HikariDataSource ds = new HikariDataSource(hikariConfig);
		return ds;
	}

	@Bean(name = "userEntityManagerFactrory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder,
			@Qualifier("userDataSource") DataSource dataSource) {
		Map<String, Object> prop = new HashMap<>();
		prop.put("hibernate.hbm2ddl.auto", "update");
		prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		return entityManagerFactoryBuilder.dataSource(dataSource).packages("com.java.sunny.model")
				.persistenceUnit("MyUser").persistenceUnit("Roles").properties(prop).build();
	}
	
	@Bean(name = "userTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("userEntityManagerFactrory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
