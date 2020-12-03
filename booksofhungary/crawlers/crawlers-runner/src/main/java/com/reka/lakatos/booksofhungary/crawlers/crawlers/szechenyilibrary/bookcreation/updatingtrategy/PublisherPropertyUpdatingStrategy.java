package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class PublisherPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    @Override
    public void updateProperty(Book book, String property) {
        setPublisher(property, book);
    }

    private void setPublisher(String value, Book book) {
        Pattern publisherPattern = Pattern.compile("[^:]*?(?=[,])");
        Matcher matcher = publisherPattern.matcher(value);
        if (matcher.find()) {
            book.setPublisher(matcher.group().trim());
        }
    }
}
