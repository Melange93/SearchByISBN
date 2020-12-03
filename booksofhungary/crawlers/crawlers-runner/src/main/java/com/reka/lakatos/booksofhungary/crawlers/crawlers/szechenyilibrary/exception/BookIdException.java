package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class BookIdException extends RuntimeException {
    public BookIdException(String message) {
        super(message);
    }
}
