package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation;

import com.reka.lakatos.searchbyisbn.document.Book;

public class DefaultAuthorPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        book.setAuthor(property.trim());
    }
}
