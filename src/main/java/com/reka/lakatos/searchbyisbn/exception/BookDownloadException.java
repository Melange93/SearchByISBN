package com.reka.lakatos.searchbyisbn.exception;

public class BookDownloadException extends RuntimeException {
    public BookDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
