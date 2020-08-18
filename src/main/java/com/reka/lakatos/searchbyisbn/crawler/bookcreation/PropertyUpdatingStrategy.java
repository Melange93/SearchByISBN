package com.reka.lakatos.searchbyisbn.crawler.bookcreation;

import com.reka.lakatos.searchbyisbn.document.Book;

public interface PropertyUpdatingStrategy {

    void updateProperty(Book book, String property);
}
