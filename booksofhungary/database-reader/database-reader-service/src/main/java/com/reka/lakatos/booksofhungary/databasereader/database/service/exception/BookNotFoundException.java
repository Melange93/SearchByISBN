package com.reka.lakatos.booksofhungary.databasereader.database.service.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
