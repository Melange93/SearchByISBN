package com.reka.lakatos.booksofhungary.crawlers.init;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.crawler.BookCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.reka.lakatos.booksofhungary.crawlers.service.BookService;
import com.reka.lakatos.booksofhungary.crawlers.service.registrationservice.RegistryResult;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final BookCrawler bookCrawler;
    private final BookService bookService;

    @Override
    public void run(String... args) {
        log.info("Start crawling");

        List<Book> nextBooks = bookCrawler.getNextBooks();
        while (nextBooks != null) {
            nextBooks.forEach(this::saveBook);

            nextBooks = bookCrawler.getNextBooks();
        }

        log.info("Finished");
    }

    private void saveBook(Book book) {
        RegistryResult registryResult = bookService.saveBook(book);

        log.info("Save result: {} isbn: {}", registryResult, book.getIsbn());
    }
}
