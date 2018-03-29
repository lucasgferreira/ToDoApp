package com.todoapp;

import java.net.URISyntaxException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class DataConfigurationPostgreSQL {
	@Bean
    public BasicDataSource dataSource() throws URISyntaxException {

        String username = "tdhimrjnwqcqzm";
        String password = "8784c42710b8868f63fb70b0bad3a783d2d769dce1713e4e124bf5280d508a01";
        String dbUrl = "jdbc:postgresql://postgres://tdhimrjnwqcqzm:8784c42710b8868f63fb70b0bad3a783d2d769dce1713e4e124bf5280d508a01@ec2-54-243-28-109.compute-1.amazonaws.com:5432/d4k3e95c606ooa";

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }
}
