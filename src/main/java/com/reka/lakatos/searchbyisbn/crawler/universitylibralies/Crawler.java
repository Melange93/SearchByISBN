package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.crawler.BookCrawler;
import com.reka.lakatos.searchbyisbn.document.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class Crawler implements BookCrawler {

    private final BookListCreator bookListCreator;

    @Override
    public List<Book> getNextBooks() {
        List<Book> bookList = bookListCreator.createBookList("9789632440453");
        List<Book> bookList1 = bookListCreator.createBookList("9789635033737");
        System.out.println(bookList);
        System.out.println(bookList1);
        return bookListCreator.createBookList("9634317960");
    }
}
