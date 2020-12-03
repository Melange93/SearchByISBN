package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.session.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class SessionActivationException extends RuntimeException {
    public SessionActivationException(String message, Throwable cause) {
        super(message, cause);
    }
}
