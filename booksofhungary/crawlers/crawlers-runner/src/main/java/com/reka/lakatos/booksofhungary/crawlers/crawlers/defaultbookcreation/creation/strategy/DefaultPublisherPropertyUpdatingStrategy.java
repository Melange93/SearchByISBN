package com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.strategy;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultPublisherPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String PUBLISHER_REGEX_BETWEEN_COLON_AND_COMA = "(?<=[:])[^:]*?(?=[,])";

    @Override
    public void updateProperty(Book book, String property) {
        setPublisher(property, book);
    }

    private void setPublisher(String value, Book book) {
        Matcher matcher = Pattern.compile(PUBLISHER_REGEX_BETWEEN_COLON_AND_COMA).matcher(value);
        if (matcher.find()) {
            book.setPublisher(matcher.group().trim());
        }
    }
}
