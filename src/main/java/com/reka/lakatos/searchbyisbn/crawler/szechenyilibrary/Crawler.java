package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Crawler implements BookCrawler {

    @Override
    public List<Book> getNextBooks() {
        log.info("Széchenyi");
        return null;
    }
}
