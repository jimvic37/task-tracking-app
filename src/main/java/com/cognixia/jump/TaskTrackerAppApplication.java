package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Taks tracker App", version = "1.0",
		description = "API that allows CRUD method for each tables"))
public class TaskTrackerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerAppApplication.class, args);
	}
	
}