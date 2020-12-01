package com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultISBNPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String ISBN10_AND_ISBN13 =
            "(\\d{1,3}([- ])\\d{1,5}\\2\\d{1,7}\\2\\d{1,6}\\2(\\d|X))" +
            "|(\\d{1,5}([- ])\\d{1,7}\\5\\d{1,6}\\5(\\d|X))" +
            "|(\\d{13})" +
            "|(\\d{10})";

    @Override
    public void updateProperty(Book book, String property) {
        setISBN(book, property);
    }

    private void setISBN(Book book, String property) {
        Optional<String> isbn = findISBN(property);
        isbn.ifPresent(book::setIsbn);
    }

    private Optional<String> findISBN(final String property) {
        Matcher isbnMatcher = Pattern.compile(ISBN10_AND_ISBN13).matcher(property);
        if (isbnMatcher.find()) {
            String result = isbnMatcher.group();
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
