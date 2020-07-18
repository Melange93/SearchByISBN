package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;

public class DatePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        book.setYearOfRelease(property.trim());
    }
}
