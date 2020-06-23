package com.reka.lakatos.searchbyisbn.init;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library.BookCreator;
import com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library.MetropolitanErvinSzaboLibraryCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.service.BookService;
import com.reka.lakatos.searchbyisbn.service.util.RegistryResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private BookCrawler bookCrawler;
    @Autowired
    private BookService bookService;

    @Override
    public void run(String... args) throws Exception {

        log.info("Start");
        for (int i = 0; i < 2; i++) {
            List<Book> nextBooks = bookCrawler.getNextBooks();
            log.info(String.valueOf(nextBooks));
            for (Book book : nextBooks) {
                RegistryResult registryResult = bookService.saveBook(book);
                log.info(String.valueOf(registryResult));
            }
        }
        log.info("Finished");
    }

    @Bean
    public ISBNValidator getISBNValidator() {
        return new ISBNValidator();
    }
}
