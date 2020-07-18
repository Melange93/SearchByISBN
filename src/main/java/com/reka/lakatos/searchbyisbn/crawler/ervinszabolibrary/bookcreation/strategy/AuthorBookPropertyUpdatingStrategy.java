package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;

public class AuthorBookPropertyUpdatingStrategy implements BookPropertyUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        book.setAuthor(property.trim());
    }
}
