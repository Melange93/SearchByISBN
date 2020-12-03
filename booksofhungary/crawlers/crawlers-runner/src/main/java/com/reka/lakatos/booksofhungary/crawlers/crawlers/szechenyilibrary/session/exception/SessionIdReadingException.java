package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.session.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class SessionIdReadingException extends RuntimeException {
    public SessionIdReadingException(String message) {
        super(message);
    }
}
