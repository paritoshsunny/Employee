package com.java.sunny.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Component
public class DataSourceConfig {

	@Bean
	public DataSource primaryDataSource() {
		
		HikariConfig hikariConfig= new HikariConfig();
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.setUsername("root");
		hikariConfig.setPassword("Singh@24#");
		hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/Employee");
		hikariConfig.setMaximumPoolSize(5);
		hikariConfig.setConnectionTimeout(30000);
		hikariConfig.setPoolName("EmployeePool");
		HikariDataSource ds = new HikariDataSource(hikariConfig);
		return ds;
	}

}
