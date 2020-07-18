package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublisherBookPropertyUpdatingStrategy implements BookPropertyUpdatingStrategy {
    @Override
    public void updateProperty(Book book, String property) {
        setPublisher(property, book);
    }

    private void setPublisher(String value, Book book) {
        Pattern publisherPattern = Pattern.compile("(?<=[:])[^:]*?(?=[,])");
        Matcher matcher = publisherPattern.matcher(value);
        if (matcher.find()) {
            book.setPublisher(matcher.group().trim());
        }
    }
}
