package com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.creation;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ISBNPropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private static final String ISBN13_REGEX = "((?:[\\dX]{13})|(?:[\\d\\-\\sX]{17}))";
    private static final String ISBN10_REGEX = "((?:[\\dX]{10})|(?:[\\d\\-\\sX]{13}))";
    private static final String[] HUNGARY_ISBN_STARTING_NUMBERS = {"978963", "978615", "963"};

    @Override
    public void updateProperty(Book book, String property) {
        setISBN(book, property);
    }

    private void setISBN(Book book, String property) {
        Optional<String> isbn13 = findISBN(property, ISBN13_REGEX);
        isbn13.ifPresent(book::setIsbn);
        if (isbn13.isEmpty()) {
            Optional<String> isbn10 = findISBN(property, ISBN10_REGEX);
            isbn10.ifPresent(book::setIsbn);
        }
    }

    private Optional<String> findISBN(String property, String ISBNRegex) {
        Pattern isbnPattern = Pattern.compile(ISBNRegex);
        Matcher isbnMatcher = isbnPattern.matcher(property);
        if (isbnMatcher.find()) {
            String result = isbnMatcher.group();
             return isHungaryISBNNumber(result) ? Optional.of(result) : Optional.empty();
        }
        return Optional.empty();
    }

    private boolean isHungaryISBNNumber(String ISBN) {
        for (String startingNumber : HUNGARY_ISBN_STARTING_NUMBERS) {
            if (ISBN.startsWith(startingNumber)) {
                return true;
            }
        }
        return false;
    }
}
