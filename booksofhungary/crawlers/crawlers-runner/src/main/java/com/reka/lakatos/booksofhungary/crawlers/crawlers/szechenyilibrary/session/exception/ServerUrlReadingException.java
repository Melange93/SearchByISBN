package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.session.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class ServerUrlReadingException extends RuntimeException {
    public ServerUrlReadingException(String message) {
        super(message);
    }
}
