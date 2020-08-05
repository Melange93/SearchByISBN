package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.document.Book;

public class AuthorPropertiesUpdatingStrategy implements PropertiesUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        book.setAuthor(property.trim());
    }
}
