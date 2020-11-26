package com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.creation;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
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
