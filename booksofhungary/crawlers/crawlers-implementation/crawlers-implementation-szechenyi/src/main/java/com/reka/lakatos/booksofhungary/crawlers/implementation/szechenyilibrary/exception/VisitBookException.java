package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class VisitBookException extends RuntimeException {
    public VisitBookException(String message, Throwable cause) {
        super(message, cause);
    }
}
