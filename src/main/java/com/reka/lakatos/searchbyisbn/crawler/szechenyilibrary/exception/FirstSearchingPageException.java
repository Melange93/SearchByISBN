package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class FirstSearchingPageException extends RuntimeException {
    public FirstSearchingPageException(String message, Throwable cause) {
        super(message, cause);
    }
}
