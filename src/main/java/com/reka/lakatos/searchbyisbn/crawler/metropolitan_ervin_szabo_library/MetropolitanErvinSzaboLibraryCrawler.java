package com.reka.lakatos.searchbyisbn.crawler.metropolitan_ervin_szabo_library;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;

@ConditionalOnProperty(name = "ervinszabo")
public class MetropolitanErvinSzaboLibraryCrawler implements BookCrawler {

    @Override
    public List<Book> getNextBooks() {
        return null;
    }
}
