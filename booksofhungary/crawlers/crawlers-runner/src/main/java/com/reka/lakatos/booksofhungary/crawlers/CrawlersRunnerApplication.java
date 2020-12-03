package com.reka.lakatos.booksofhungary.crawlers;

import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrawlersRunnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlersRunnerApplication.class, args);
	}

	@Bean
	public ISBNValidator getISBNValidator() {
		return new ISBNValidator();
	}
}
