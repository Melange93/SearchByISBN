package com.reka.lakatos.booksofhungary.crawlers.implementation.universitylibraries.bookcreationstratgy.creation;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class PublisherPropertyUpdatingStrategy implements PropertyUpdatingStrategy {
    @Override
    public void updateProperty(Book book, String property) {
        setPublisher(book, property);
    }

    private void setPublisher(Book book, String property) {
        Pattern publisherPattern = Pattern.compile("[^\\[\\]]*");
        Matcher matcher = publisherPattern.matcher(property);
        if (matcher.find()) {
            book.setPublisher(matcher.group().trim());
        }
    }
}
