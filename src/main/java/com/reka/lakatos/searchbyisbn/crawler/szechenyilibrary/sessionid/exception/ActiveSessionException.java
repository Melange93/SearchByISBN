package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.exception;

public class ActiveSessionException extends RuntimeException {
    public ActiveSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
