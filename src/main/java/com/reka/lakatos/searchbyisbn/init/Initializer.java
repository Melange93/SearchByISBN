package com.reka.lakatos.searchbyisbn.init;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library.MetropolitanErvinSzaboLibraryCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.repository.BookRepository;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class Initializer implements CommandLineRunner {

    private BookRepository bookRepository;

    public Initializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public ISBNValidator getISBNValidator() {
        return new ISBNValidator();
    }
}
