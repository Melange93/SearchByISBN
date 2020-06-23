package com.reka.lakatos.searchbyisbn;

import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SearchByIsbnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchByIsbnApplication.class, args);
    }

    @Bean
    public ISBNValidator getISBNValidator() {
        return new ISBNValidator();
    }
}
