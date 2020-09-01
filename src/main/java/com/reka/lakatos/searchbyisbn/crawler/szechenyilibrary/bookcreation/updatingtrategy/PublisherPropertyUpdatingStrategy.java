package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class PublisherPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;

    @Override
    public void updateProperty(Book book, String property) {
        setPublisher(property, book);
        setDate(book, property);
    }

    private void setPublisher(String value, Book book) {
        Pattern publisherPattern = Pattern.compile("[^:]*?(?=[,])");
        Matcher matcher = publisherPattern.matcher(value);
        if (matcher.find()) {
            book.setPublisher(matcher.group().trim());
        }
    }

    private void setDate(Book book, String property) {
        String dateRegex = "[\\d]{4}";
        Matcher matcher = Pattern.compile(dateRegex).matcher(property);
        if (isValidDate(matcher) && matcher.find()) {
            book.getEditions().get(INDEX_OF_BASIC_EDITION).setYearOfRelease(Integer.parseInt(matcher.group()));
        }
    }

    private boolean isValidDate(Matcher matcher) {
        int counter = (int) matcher.results().count();
        matcher.reset();
        return counter == 1;
    }
}
