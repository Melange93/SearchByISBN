package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.session.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class SessionException extends RuntimeException {
    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
