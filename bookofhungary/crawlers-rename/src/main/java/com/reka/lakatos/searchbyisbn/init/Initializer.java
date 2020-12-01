package com.reka.lakatos.searchbyisbn.init;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.service.BookService;
import com.reka.lakatos.searchbyisbn.service.util.RegistryResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
