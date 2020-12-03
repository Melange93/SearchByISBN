package com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.strategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.PropertyUpdatingStrategy;

public class DefaultAuthorPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        book.setAuthor(property.trim());
    }
}
