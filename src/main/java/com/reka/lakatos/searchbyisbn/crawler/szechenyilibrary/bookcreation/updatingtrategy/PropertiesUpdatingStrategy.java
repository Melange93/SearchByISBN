package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.document.Book;

public interface PropertiesUpdatingStrategy {
    void updateProperty(Book book, String property);
}
