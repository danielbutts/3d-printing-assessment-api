package com.github.danielbutts.partsanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class PortfolioAnalysisApplication {

	@GetMapping("")
	public String hello() {
		return "Hello!";
	}

	public static void main(String[] args) {
		SpringApplication.run(PortfolioAnalysisApplication.class, args);
	}
}
