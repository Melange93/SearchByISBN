package com.reka.lakatos.searchbyisbn.init;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library.MetropolitanErvinSzaboLibraryCrawler;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initializer implements CommandLineRunner {

    private BookCrawler bookCrawler;

    public Initializer() {
        this.bookCrawler = new MetropolitanErvinSzaboLibraryCrawler();
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public ISBNValidator getISBNValidator() {
        return new ISBNValidator();
    }
}
