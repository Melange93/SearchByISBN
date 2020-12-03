package com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;

public interface PropertyUpdatingStrategy {

    void updateProperty(Book book, String property);
}
