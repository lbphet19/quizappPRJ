package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.project.properties.FileStorageProperties;



@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class SpringBootQuizAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootQuizAppApplication.class, args);
	}

}
