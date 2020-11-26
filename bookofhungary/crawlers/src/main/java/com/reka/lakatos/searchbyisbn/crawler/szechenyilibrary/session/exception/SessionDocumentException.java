package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class SessionDocumentException extends RuntimeException {
    public SessionDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
