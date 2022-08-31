package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;

@SpringBootApplication
@MapperScan({ "com.mapper" })
@ServletComponentScan
@EnableTransactionManagement
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = { "com" })
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
