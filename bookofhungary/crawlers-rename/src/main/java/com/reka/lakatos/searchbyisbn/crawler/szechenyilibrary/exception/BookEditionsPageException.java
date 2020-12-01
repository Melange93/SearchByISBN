package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class BookEditionsPageException extends RuntimeException {
    public BookEditionsPageException(String message, Throwable cause) {
        super(message, cause);
    }
}
