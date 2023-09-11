package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Pokémon Team Builder by Team Altaria", version = "1.0",
		description = "API that allows the creation and management of a 6-member pokemon team,"
				+ " and displays information regarding the strengths and weaknesses of the team."))
public class TaskTrackerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerAppApplication.class, args);
	}
}