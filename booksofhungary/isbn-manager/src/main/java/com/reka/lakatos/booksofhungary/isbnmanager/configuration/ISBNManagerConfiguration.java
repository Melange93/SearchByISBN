package com.reka.lakatos.booksofhungary.isbnmanager.configuration;

import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ISBNManagerConfiguration {

    @Bean
    public ISBNValidator getISBNValidator() {
        return new ISBNValidator();
    }
}
