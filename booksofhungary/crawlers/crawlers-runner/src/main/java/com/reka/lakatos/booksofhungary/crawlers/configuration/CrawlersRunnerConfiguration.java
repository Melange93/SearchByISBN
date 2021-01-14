package com.reka.lakatos.booksofhungary.crawlers.configuration;

import com.reka.lakatos.booksofhungary.isbnmanager.service.BookISBNManager;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlersRunnerConfiguration {

    @Bean
    public ISBNValidator getISBNValidator() {
        return new ISBNValidator();
    }

    @Bean
    public BookISBNManager getBookISBNManager() {
        return new BookISBNManager(getISBNValidator());
    }
}
