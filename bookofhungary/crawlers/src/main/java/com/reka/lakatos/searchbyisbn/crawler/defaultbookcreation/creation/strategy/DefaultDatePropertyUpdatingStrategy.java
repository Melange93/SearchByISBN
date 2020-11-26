package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultDatePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final int INDEX_OF_BASIC_EDITION = 0;

    @Override
    public void updateProperty(Book book, String property) {
        setDate(book, property);
    }

    private void setDate(Book book, String property) {
        String dateRegex= "[\\d]{4}";
        Matcher matcher = Pattern.compile(dateRegex).matcher(property);
        long count = matcher.results().count();
        matcher.reset();
        if (count == 1 && matcher.find()) {
            book.getEditions().get(INDEX_OF_BASIC_EDITION).setYearOfRelease(Integer.parseInt(matcher.group()));
        }
    }
}
