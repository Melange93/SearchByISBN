package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class RelatedBookException extends RuntimeException {
    public RelatedBookException(String message, Throwable cause) {
        super(message, cause);
    }
}
