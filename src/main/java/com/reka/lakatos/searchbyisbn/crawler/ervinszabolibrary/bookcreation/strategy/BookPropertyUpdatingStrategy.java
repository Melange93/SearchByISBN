package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;

public interface BookPropertyUpdatingStrategy {

    void updateProperty(Book book, String property);
}
