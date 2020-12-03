package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class ServerUrlCleaningException extends RuntimeException {
    public ServerUrlCleaningException(String message) {
        super(message);
    }
}
