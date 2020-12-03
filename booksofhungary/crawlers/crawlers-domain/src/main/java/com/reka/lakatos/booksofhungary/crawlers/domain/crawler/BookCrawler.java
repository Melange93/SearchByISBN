package com.reka.lakatos.booksofhungary.crawlers.domain.crawler;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;

import java.util.List;

public interface BookCrawler {

    List<Book> getNextBooks();
}
